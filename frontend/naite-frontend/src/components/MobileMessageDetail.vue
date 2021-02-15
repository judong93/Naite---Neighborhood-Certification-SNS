<template>
    <div id='mobilemessagedetail'>        
        <div class="mobilemessageDetailTitle">
                <i class="fas fa-arrow-left" @click='backtosecondstate'></i>
                <h3>{{otherNick}}님과의 채팅방</h3>
        </div>         
        <div style='margin-top:6%'></div>
        <div class="mobilechatList" v-for='(chat,idx) in chatDetail' :key='idx'>
            <div class='mobileotherMessage' v-if='chat.userNick !== myNick'>                
                <img src="../assets/cha2.png" v-if='(chatDetail[idx-1]&&chatDetail[idx-1].userPic !== chat.userPic) || !chatDetail[idx-1] || chatDetail[idx-1]&&chatDetail[idx-1].time !== chat.time'>
                <div style = 'width: 70px;' v-else></div>
                <div class='mobilemessageInfo'>
                    <span v-if='(chatDetail[idx-1]&&chatDetail[idx-1].userPic !== chat.userPic) || !chatDetail[idx-1]||chatDetail[idx-1]&&chatDetail[idx-1].time!==chat.time' >{{chat.userNick}}</span>
                    <div style='margin:-10px' v-else></div>
                    <br>
                    <div>
                        <div class='mobilemessageContent'>{{chat.message}}</div>
                        <div v-if='(chatDetail[idx+1]&&chatDetail[idx+1].time!==chat.time &&chat.userNick===chatDetail[idx+1].userNick) || !chatDetail[idx+1] || (chatDetail[idx+1] && chatDetail[idx+1].userNick !== chat.userNick)'>{{chat.time}}</div>
                    </div>
                </div>

            </div>


            <div class="mobilemyMessage" v-else>
                

                <div class='mobilemymessageInfo'>
                    <span v-if='(chatDetail[idx-1]&&chatDetail[idx-1].userPic !== chat.userPic) || !chatDetail[idx-1]||chatDetail[idx-1]&&chatDetail[idx-1].time!==chat.time' >{{chat.userNick}}</span>
                    <div style='margin:-10px' v-else></div>
                    <br>
                    <div>
                        <div v-if='(chatDetail[idx+1]&&chatDetail[idx+1].time!==chat.time &&chat.userNick===chatDetail[idx+1].userNick) || !chatDetail[idx+1] || (chatDetail[idx+1] && chatDetail[idx+1].userNick !== chat.userNick)' class='myTime'>{{chat.time}}</div>
                        <div class='mobilemymessageContent'>{{chat.message}}</div>
                    </div>
                </div>

                <img src="../assets/naitelogo.png" v-if='(chatDetail[idx-1]&&chatDetail[idx-1].userPic !== chat.userPic) || !chatDetail[idx-1] || chatDetail[idx-1]&&chatDetail[idx-1].time !== chat.time'>
                <div style = 'width: 70px;' v-else></div>
            </div>


        </div>
        <div class='mobileMessageLoading'>
            <div class="spinner-border text-primary" role="status">
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
        <input type="text" class='mobilechatInput' v-model='myMessage' @keypress.enter="sendMessage()">
        <button @click='sendMessage()'>전송</button>
        
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
    name:'MobileMessageDetail',
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
                const loadingWindow = document.querySelector('.mobileMessageLoading')
                loadingWindow.style.display = 'none'                
            }, 2000);
   
            
        },
        decoderUser:function(){
            const token = localStorage.getItem('jwt')
            const decoder = jwt_decode(token)            
            this.myNick = decoder.user.userNick
            this.myPic = decoder.user.userPic
        },
        scrollDown:function(){
            const chattingRoom = document.getElementById('mobilemessagedetail')            
            chattingRoom.scrollTop = chattingRoom.scrollHeight  
            console.log(chattingRoom)
        }
        
    },
    watch:{
        roomNo:function(){            
            this.connectRoom(this.roomNo)
            this.connect()
            this.decoderUser()            
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
#mobilemessagedetail {
    display: none;
}
@media screen and (max-width:501px) {
    #mobilemessagedetail{
        display: block;
        position: relative;
        width:100%;
        height:93%;
        background-color: rgb(214, 213, 213);
        top:0%;
        color:black;
        font-family: font1;
        overflow: auto;
        padding-bottom: 3%;
    }

    .mobilemessageDetailTitle{
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
    .mobilemessageDetailTitle > i {
        font-size: 30px;
        
        margin-right: 2%;
    }

    .mobilechatInput {
        position:fixed;
        background-color:#A87A4F;
        bottom:7%;
        height: 6.5%;
        width: 80%;
        left:0;
        text-align: right;
        padding:1%;
        border-radius: 1%;
        /* border:1px solid black; */
        border:none;
        outline: none;
        box-shadow: 0 0 5px 0 black;
        color:white;
    }

    .mobilechatInput+button {
        position:fixed;
        bottom:7%;
        height: 6.5%;
        width: 20%;
        right:0%;
        border:none;
        outline: none;
        background-color: #A87A4F;
        color:white;
        box-shadow: 0 0 5px 0 black;
        
    }
    .mobilechatList {
        /* height:10%; */
    }

    .mobilechatList > .mobileotherMessage >img {
        width: 60px;
        height: 60px;
        border-radius: 100%;
        border: 1px rgb(187, 187, 187) solid;
        margin-right: 10px;

    }

    .mobileotherMessage {
        display:flex;
        padding: 1%;
        text-align: left;
    }



    .mobilemessageContent{
        background-color: rgb(241, 234, 234);
        border-radius: 5px;
        padding:1% 1% 1% 3%;
        width: 120%;

    }


    .mobilemessageInfo {
        max-width: 40%;    
    }

    .mobilemessageInfo > div {
        display:flex;
        width:120%;
        align-items: flex-end;

    }

    .mobilemessageContent + div {
        white-space: nowrap;
        font-size: 5px;
        margin: 0 10px;
    }

    .mobilemyMessage {
        padding-right: 1%;
        display:flex;
        justify-content: flex-end;
        width:100%;
    }
    .mobilemyMessage > img {
        width: 60px;
        height:60px;
        overflow: hidden;
        border-radius: 100%;
        margin-left: 10px;
        
    }

    .mobilemymessageInfo {
        text-align: right;
        margin-right: 5px;    
        max-width:40%;
    }

    .mobilemymessageInfo > div {
        display:flex;
        align-items: flex-end;
    
        
    }
    .mobilemymessageContent{
        background-color: rgb(241, 234, 234);
        border-radius: 5px;    
        text-align: left;
        padding:1% 0;    
        /* width: %;     */

    }

    .mobilemyTime {
        font-size: 5px;
        white-space: nowrap;
        margin-right: 10px;
    }

    .mobileMessageLoading {
        position:fixed;
        top:0;
        width:100%;
        height:100%;
        background-color: rgba(211, 211, 211, 0.521);
    }
    .spinner-border{
        position:fixed;
        top:45%;
        left:45%;
        
    }

    
}



</style>