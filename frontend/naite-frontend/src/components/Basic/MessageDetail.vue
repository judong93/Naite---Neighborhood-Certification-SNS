<template>
    <div id="messagedetail">
        <header>
            <div>
                <i class="fas fa-arrow-left" @click='backtosecondstate()'></i>
                <h3>{{otherNick}}님과의 채팅방</h3>
            </div>
            <div>
                <button @click='goProfile'>프로필 바로가기</button>
            </div>
        </header>

        <body>
            <div>
                <div v-for='(chat,idx) in chatDetail' :key='idx'>
                    <div v-if='chat.userNick !== myNick' class='otherChat'>
                        <div>
                            <div style='border-radius:30%;display:block;overflow:hidden;width:50px;margin-right:10px;'>
                                <img :src="chat.userPic" alt="" width='50px' height='50px' v-if='idx===0 || chatDetail[idx-1].userNick !==chat.userNick&& chatDetail[idx-1].time !==chat.time ||chatDetail[idx-1].userNick !==chat.userNick'>
                            </div>
                        </div>
                        <div class='otherChatmsg'>
                            <header v-if='idx===0 || chatDetail[idx-1].userNick !==chat.userNick&& chatDetail[idx-1].time !==chat.time||chatDetail[idx-1].userNick !==chat.userNick'>
                                {{chat.userNick}}
                            </header>
                            <div>
                                <body>
                                    <div>
                                        {{chat.message}}
                                    </div>
                                </body>
                                <footer v-if='!idx|| chatDetail[idx+1]&&chatDetail[idx+1].userNick!==chat.userNick||!chatDetail[idx+1]'>
                                    {{createdSimple(chat.time)}}
                                </footer>
                            </div>
                        </div>
                    </div>
                    <div v-else class='myChat'>
                        <body >
                            {{chat.message}}
                        </body>
                        <footer  v-if='!idx|| chatDetail[idx+1]&&chatDetail[idx+1].userNick!==chat.userNick||!chatDetail[idx+1]'>
                            {{createdSimple(chat.time)}}
                        </footer>
                    </div>

                </div>

            </div>

        </body>
        <footer>
            <input type="text" class='chatInput' v-model='myMessage' @keypress.enter="sendMessage()">
            <button @click='sendMessage()'><i class="fas fa-paper-plane"></i> 전송</button>
        </footer>

        <div class='MessageLoading'>
            <div class="spinner-border text-primary" role="status">
                <span class="sr-only">Loading...</span>
            </div>
            <vue-typer class='typerConnecting'
                :text="['이웃을 부르는중']"
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

export default {
    name:'MessageDetail',
    props:{
        roomNo:Number,
        otherNick:String,
        otherPic:String,
        otherUserNo:[String,Number]
        
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
        goProfile:function(){
            this.$router.push({name:'Profile', params:{userNo:this.otherUserNo}})
            this.backtosecondstate()
        },
        backtosecondstate:function(){
            this.$emit('backtosecondstate',this.otherNick)
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
                'userPic': this.otherPic,
                "userOwn": recv.userOwn,
            })  
            setTimeout(() => {
                this.scrollDown()
            }, 10);          
            

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
                        that.sock = new SockJS("https://i4a402.p.ssafy.io/api/ws-stomp");
                        that.ws = Stomp.over(that.sock);
                        that.connect();
                    },1000);
            }
            })
            setTimeout(() => {
                this.scrollDown()
                const loading = document.querySelector('.MessageLoading')
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
            const chattingRoom = document.querySelector('#messagedetail>body>div')
            chattingRoom.scrollTop = chattingRoom.scrollHeight  
        }
        
    },
    computed:{
        createdSimple(){
            return (date) => {
                if (!date){
                    return;
                }
                var dateArray = date.split('-')
                if (date.length > 10) {
                    var timeArray = dateArray[2].split(' ')
                    return dateArray[1]+'/'+dateArray[2][0] + dateArray[2][1]+' '+timeArray[0] +':'+timeArray[1][0]+timeArray[1][1]
                } else {
                    return date
                }
            }
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
        let that = this
        window.addEventListener('keyup',function(e){
            if (e.key.toLowerCase()==='escape'){
                that.backtosecondstate()
            }
        })
    }
}

</script>
<style>
#messagedetail{
        display:block;
        width:100%;
        max-height:100%;
        font-family: font1;
    }
#messagedetail > header {
    position:relative;
    display:flex;
    padding:2% 1%;
    width:100%;
    background-color: #a87a4ffa;
    color:white;        
}
#messagedetail>header>div:nth-child(1)>i{
    margin:0 3%;
    padding:0.5% 0;
    font-size: 24px;
}
#messagedetail>header>div:nth-child(1){
    display:flex;
    width:90%;
}
#messagedetail>header>div:nth-child(2)>button{
    border:none;
    outline: none;
    background-color: transparent;
    width:100%;
    color:white;
    transition:0.3s
}

#messagedetail>body{
    position:relative;
}
#messagedetail>body>div{
    position:relative;
    background-color: rgb(241, 236, 183);
    width:100%;
    height:50vh;
    overflow: auto;
    padding:10px;
}
#messagedetail>body>div::-webkit-scrollbar { width: 10px;}
#messagedetail>body>div::-webkit-scrollbar-track { background-color:rgba(0, 0, 0, 0.5);border-radius: 10px;  }
#messagedetail>body>div::-webkit-scrollbar-thumb { background: #e6e3e0f5;border-radius: 10px;  }
#messagedetail>body>div::-webkit-scrollbar-thumb:hover { background: #e68c42; } 
#messagedetail>body>div::-webkit-scrollbar-thumb:active { background: #e68c42; }
#messagedetail>body>div::-webkit-scrollbar-button { display: none; } 


#messagedetail>body>div>div {
    margin-bottom: 10px;
}

.otherChat {
    display:flex;
}
.otherChatmsg {
    font-family: font1;
}

.otherChatmsg>header {
    font-size: 12px;
}
.otherChatmsg>div>body {
    background-color: rgba(70, 226, 70, 0.534);
    font-family: font1;
    border-radius: 10px;
    margin-right: 1%; 
    width:100%;
    padding-right: 10px;
}
.otherChatmsg>div>body>div{
    padding:10px 10px;
    font-size: 14px;
    width:120%;
    max-width: 700px;
    white-space: normal;
}

.otherChatmsg>div {
    display:flex;
    align-items: flex-end;
    
}
.otherChatmsg>div>footer {
    font-family: font1;
    font-size: 5px;
    white-space: nowrap;
}


.myChat {
    display:flex;
    flex-direction: row-reverse;
    justify-content: flex-start;
    align-items:flex-end;
}
.myChat>body {
    padding:10px 15px;
    font-size: 14px;
    background-color: #a87a4fbe;
    font-family: font1;
    border-radius: 10px;        
    margin-left: 1%;
    min-width: 50px;
    width:fit-content;
    max-width: 700px;
    white-space: normal;
    text-align: left;
}

.myChat>footer {
    font-size: 5px;
    white-space: nowrap;
    font-family: font1;
    
}


#messagedetail > footer {
    position:relative;
    width:100%;
    height: 100%;
    background-color: white;
}
.chatInput {
    position:relative;
    width:90%;
    height:100%;
    border:none;
    outline: none;
    padding:1.5%; 
    border-radius: 10px;   
    background-color: white;
    border: 1px solid black;
}
.chatInput +button {
    width:10%;
    border:none;padding:1.5%; 
    outline: none;
    background-color: #a87a4fbe;
    color:white;
    border-radius: 10px;
}





.MessageLoading {
    position:fixed;
    top:0%;
    width:100%;
    height:100%;
    background-color: rgba(211, 211, 211, 0.521);
}
.MessageLoading>.spinner-border{
    position:fixed;
    top:48%;
    left:49%;
    
}
.typerConnecting {
    color:white;
    position:absolute;
    top:55%;
    left:50%;
    transform:translateX(-50%)
}


    


</style>