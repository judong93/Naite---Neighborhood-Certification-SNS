<template>
    <div id='messagedetail'>        
        <div class="messageDetailTitle">
                <i class="fas fa-arrow-left" @click='backtosecondstate'></i>
                <h3>{{otherNick}}님과의 채팅방</h3>
        </div>         
        <div style='margin-top:6%'></div>
        <div class="chatList" v-for='(chat,idx) in chatDetail' :key='idx'  @change="scrolltobottom">
            <div class='otherMessage' v-if='chat.userNick !== myNick'>                
                <img src="../../assets/cha2.png" v-if='(chatDetail[idx-1]&&chatDetail[idx-1].userPic !== chat.userPic) || !chatDetail[idx-1] || chatDetail[idx-1]&&chatDetail[idx-1].time !== chat.time'>
                <div style = 'width: 70px;' v-else></div>
                <div class='messageInfo'>
                    <span v-if='(chatDetail[idx-1]&&chatDetail[idx-1].userPic !== chat.userPic) || !chatDetail[idx-1]||chatDetail[idx-1]&&chatDetail[idx-1].time!==chat.time' >{{chat.userNick}}</span>
                    <div style='margin:-20px' v-else></div>
                    <br>
                    <div>
                        <div class='messageContent'>{{chat.message}}</div>
                        <div v-if='(chatDetail[idx+1]&&chatDetail[idx+1].time!==chat.time &&chat.userNick===chatDetail[idx+1].userNick) || !chatDetail[idx+1] || (chatDetail[idx+1] && chatDetail[idx+1].userNick !== chat.userNick)'>{{chat.time}}</div>
                    </div>
                </div>

            </div>


            <div class="myMessage" v-else>
                

                <div class='mymessageInfo'>
                    <span v-if='(chatDetail[idx-1]&&chatDetail[idx-1].userPic !== chat.userPic) || !chatDetail[idx-1]||chatDetail[idx-1]&&chatDetail[idx-1].time!==chat.time' >{{chat.userNick}}</span>
                    <div style='margin:-10px' v-else></div>
                    <br>
                    <div>
                        <div v-if='(chatDetail[idx+1]&&chatDetail[idx+1].time!==chat.time &&chat.userNick===chatDetail[idx+1].userNick) || !chatDetail[idx+1] || (chatDetail[idx+1] && chatDetail[idx+1].userNick !== chat.userNick)' class='myTime'>{{chat.time}}</div>
                        <div class='mymessageContent'>{{chat.message}}</div>
                    </div>
                </div>

                <img src="../../assets/naitelogo.png" v-if='(chatDetail[idx-1]&&chatDetail[idx-1].userPic !== chat.userPic) || !chatDetail[idx-1] || chatDetail[idx-1]&&chatDetail[idx-1].time !== chat.time'>
                <div style = 'width: 70px;' v-else></div>
            </div>
        </div>

        <input type="text" class='chatInput' v-model='myMessage' @keypress.enter="sendMessage()">
        <button @click='sendMessage()'>메시지 전송</button>
        <div class='messageLoading'>
            <div class="spinner-border text-white" role="status">
                <span class="sr-only">Loading...</span>
            </div>
            <vue-typer class='typerConnecting'
                :text="['connecting...']"
                :repeat='Infinity'
                :shuffle='false'
                initial-action='typing'
                :pre-type-delay='70'
                :type-delay='70'
                :pre-erase-delay='80'
                :erase-delay='200'
                erase-style='select-all'
                :erase-on-complete='false'
                caret-animation='solid'
            >
            </vue-typer>
            
        </div>
    </div>
</template>
<script>
import axios from 'axios'
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import jwt_decode from 'jwt-decode'


let reconnect = 0

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// let sock = new SockJS('https://i4a402.p.ssafy.io:8080/ws-stomp')
// let ws = Stomp.over(sock)
// let reconnect = 0




export default {
    name:'MessageDetail',
    props:{
        roomNo:Number,
        otherNick:String,
        
    },
    data:function(){
        return{
            chatDetail:[],
            myMessage:'',
            myNick:'',
            sock:'',
            ws:'',
            myPic:'',
            
        }
    },
    methods:{
        backtosecondstate:function(){
            this.$emit('backtosecondstate')
        },
        scrolltobottom:function(){
            console.log(document.querySelector('.chatList'))
        },
        connectRoom:function(roomNo){
            if (roomNo === 0){
                return
            }
            axios.get(`${SERVER_URL}/chat/room/${roomNo}`,this.setToken())
                .then(res =>{                    
                    this.chatDetail = res.data.data  
            
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        sendMessage:function(){
            const params={
                'roomNo':this.roomNo,
                'message':this.myMessage,

            }
            if (!this.myMessage){
                alert('내용입력')
                return
            }


            axios.post(`${SERVER_URL}/chat/message`,params,this.setToken())
                .then(res => {
                    const jsonparam = {
                        roomNo: res.data.roomNo,
                        messageNo: res.data.messageNo,
                        message: res.data.message,
                        time: res.data.time,
                        userNick: res.data.userNick,
                        userPic:this.userPic,
                        userOwn: true
                    }
                    this.ws.send("/pub/chat/message", JSON.stringify(jsonparam))
                    this.myMessage=''
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        setToken:function(){
            const token = localStorage.getItem('jwt')
            const config = {
                headers: {
                'auth-token':`${token}`
                }
            }        
            return config 
        },
        recvMessage: function(recv) {            
            this.chatDetail.push({"messageNo":recv.messageNo,
            "message":recv.message, 
            "time":recv.time, 
            "userNick": recv.userNick, 
            'userPic': recv.userPic,
            "userOwn": recv.userOwn,
            })
            this.scrollDown()
            

        },
        connect: function(){
            let that = this            
            this.ws.connect({},function(){                 
                that.ws.subscribe(`/sub/chat/room/${that.roomNo}`,function(mes){                      
                    let recv = JSON.parse(mes.body)  
                    that.recvMessage(recv)                    
                });
            }, function(){                
                if(reconnect++ <= 5) {
                    setTimeout(function() {
                        console.log('reconnect')
                        that.sock = new SockJS("https://i4a402.p.ssafy.io/api/ws-stomp");
                        that.ws = Stomp.over(that.sock);
                        that.connect();
                    },1000);
            }
            })
            setTimeout(() => {
                this.scrollDown()
                const loading = document.querySelector('.messageLoading')
                loading.style.display='none'
            }, 2200);
            
        },
        decoderUser:function(){
            const token = localStorage.getItem('jwt')
            const decoder = jwt_decode(token)            
            this.myNick = decoder.user.userNick
            this.myPic = decoder.user.userPic
        },
        scrollDown:function(){
            const chattingRoom = document.getElementById('messagedetail')
            chattingRoom.scrollTop = chattingRoom.scrollHeight  
        }
        
    },
    watch:{
        roomNo:function(){            
            this.connectRoom(this.roomNo)
            this.connect()
            this.decoderUser()
            console.log('watch')  
        },
    },
    created(){      
        let sock = new SockJS('https://i4a402.p.ssafy.io/api/ws-stomp')
        let ws = Stomp.over(sock)
        this.sock = sock
        this.ws = ws
        this.connectRoom(this.roomNo)            
        this.decoderUser()
        this.connect()
    }
}

</script>
<style>
#messagedetail{
    position: relative;
    width:100%;
    height:100%;
    background-color: rgb(214, 213, 213);
    color:black;
    font-family: font1;
    overflow: auto;
    padding-bottom: 3%;
}

.messageDetailTitle{
    position:fixed;
    background-color: #3F9F47;
    display:flex;
    text-align: left;
    padding: 1%;
    width:100%;
    box-shadow: 0 0 5px 0 black;
    margin-bottom: 2%;
    color:white;
    
    
}
.messageDetailTitle > i {
    font-size: 30px;
    
    margin-right: 2%;
}

.chatInput {
    position:fixed;
    bottom:-8%;
    height: 8%;
    width: 90%;
    left:0;
    text-align: right;
    padding:1%;
    border-radius: 1%;
    /* border:1px solid black; */
    border:none;
    outline: none;
    box-shadow: 0 0 5px 0 black;
}

.chatInput+button {
    position:fixed;
    bottom:-8%;
    height: 8%;
    width: 10%;
    right:0%;
    border:none;
    outline: none;
    background-color: #A87A4F;
    color:white;
    box-shadow: 0 0 5px 0 black;
    
}
.chatList {
    /* height:10%; */
}

.chatList > .otherMessage >img {
    width: 60px;
    height: 60px;
    border-radius: 100%;
    border: 1px rgb(187, 187, 187) solid;
    margin-right: 10px;

}

.otherMessage {
    display:flex;
    padding: 1%;
    text-align: left;
}



.messageContent{
    background-color: rgb(241, 234, 234);
    border-radius: 5px;
    padding:1% 1% 1% 3%;
    width: 120%;

}


.messageInfo {
    max-width: 40%;    
}

.messageInfo > div {
    display:flex;
    width:120%;
    align-items: flex-end;

}

.messageContent + div {
    white-space: nowrap;
    font-size: 5px;
    margin: 0 10px;
}

.myMessage {
    padding-right: 1%;
    display:flex;
    justify-content: flex-end;
    width:100%;
}
.myMessage > img {
    width: 60px;
    height:60px;
    overflow: hidden;
    border-radius: 100%;
    margin-left: 10px;
    
}

.mymessageInfo {
    text-align: right;
    margin-right: 5px;    
    max-width:40%;
}

.mymessageInfo > div {
    display:flex;
    align-items: flex-end;
   
    
}
.mymessageContent{
    background-color: rgb(241, 234, 234);
    border-radius: 5px;    
    text-align: left;
    padding:1% 0;    
    /* width: %;     */

}

.myTime {
    font-size: 5px;
    white-space: nowrap;
    margin-right: 10px;
}

.messageLoading{
    position:fixed;
    background-color: rgba(0, 0, 0, 0.384);
    top:0;
    width: 100%;
    height:108%;
}

.messageLoading > div {
    position:absolute;
    top:50%;
    left:48.5%;    
}

.typerConnecting {
    color:white;
    position:absolute;
    top:55%;
    left:50%;
    transform:translateX(-50%)
}

.vue-typer .custom.char{
    color:white;
}

.vue-typer .custom.caret.selecting{
    display: inline-block;
    background-color: black;
}

.vue-typer .custom.char.selected {
  background-color: white;
}
</style>