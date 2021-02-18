<template>
    <div id="mobilemessagedetail">
        <header>
            <div>
                <i class="fas fa-arrow-left" @click='backtosecondstate'></i>
                <h5>{{otherNick}}님과의 채팅방</h5>
            </div>
            <div>
                <button>프로필 바로가기</button>
            </div>
        </header>

        <body>
            <div>
                <div v-for='(chat,idx) in chatDetail' :key='idx'>
                    <div v-if='chat.userNick !== myNick' class='mobileotherChat'>
                        <div>
                            <div style='border-radius:30%;display:block;overflow:hidden;width:50px;margin-right:10px;'>
                                <img :src="chat.userPic" alt="" width='50px' v-if='idx===0 || chatDetail[idx-1].userNick !==chat.userNick&& chatDetail[idx-1].time !==chat.time ||chatDetail[idx-1].userNick !==chat.userNick'>
                            </div>
                        </div>
                        <div class='mobileotherChatmsg'>
                            <header v-if='idx===0 || chatDetail[idx-1].userNick !==chat.userNick&& chatDetail[idx-1].time !==chat.time||chatDetail[idx-1].userNick !==chat.userNick'>
                                {{chat.userNick}}
                            </header>
                            <div>
                                <body>
                                    {{chat.message}}
                                </body>
                                <footer v-if='!idx|| chatDetail[idx+1]&&chatDetail[idx+1].userNick!==chat.userNick||!chatDetail[idx+1]'>
                                    {{chat.time}}
                                </footer>
                            </div>
                        </div>


                    </div>
                    <div v-else class='mobilemyChat'>
                        <body >
                            {{chat.message}}
                        </body>
                        <footer  v-if='!idx|| chatDetail[idx+1]&&chatDetail[idx+1].userNick!==chat.userNick||!chatDetail[idx+1]'>
                            {{chat.time}}
                        </footer>
                    </div>

                </div>

            </div>

        </body>
        <footer>
            <input type="text" class='mobilechatInput' v-model='myMessage' @keypress.enter="sendMessage()">
            <button @click='sendMessage()'><i class="fas fa-paper-plane"></i> 전송</button>
        </footer>

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
        roomNo:[Number,String],
        otherNick:String,
        otherPic:String,
        otherNo:[Number,String]
        
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
            }, 100);
            

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
            const chattingRoom = document.querySelector('#mobilemessagedetail>body>div')
            chattingRoom.scrollTop = chattingRoom.scrollHeight           
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
        display:block;
        width:100%;
        max-height:100%;
    }
    #mobilemessagedetail > header {
        position:relative;
        display:flex;
        padding:2% 1%;
        width:100%;
        background-color: #a87a4fc4;
        color:white;        
        justify-content: space-between;
    }
    #mobilemessagedetail>header>div:nth-child(1){
        display:flex;
        width:70%;
        
    }
     #mobilemessagedetail>header>div:nth-child(2)>button {
        border:none;
        outline: none;
        background-color: transparent;
        color:white;
        font-size: 12px;
     }
    #mobilemessagedetail>header>div:nth-child(1)>i{
        margin:0 3%;
        padding:0.5% 0;
    }
    #mobilemessagedetail>body{
        position:relative;
    }
    #mobilemessagedetail>body>div{
        position:relative;
        background-color: khaki;
        width:100%;
        height:73vh;
        overflow: auto;
        padding:10px;
    }
    #mobilemessagedetail>body>div>div {
        margin-bottom: 10px;
    }

    .mobileotherChat {
        display:flex;
    }
    .mobileotherChatmsg {
        font-family: font1;
    }
    
    .mobileotherChatmsg>header {
        font-size: 12px;
    }
    .mobileotherChatmsg>div>body {
        padding:2% 0 2% 3%;
        font-size: 14px;
        background-color: rgba(70, 226, 70, 0.534);
        font-family: font1;
        border-radius: 10px;
        margin-right: 1%;
        min-width: 40px;
        width:fit-content;
        max-width: 200px;
        white-space: normal;

    }
    .mobileotherChatmsg>div {
        display:flex;
        align-items: flex-end;
        
    }
    .mobileotherChatmsg>div>footer {
        font-size: 5px;
        white-space: nowrap;
    }


    .mobilemyChat {
        display:flex;
        flex-direction: row-reverse;
        justify-content: flex-start;
        align-items:flex-end;
    }
    .mobilemyChat>body {
        padding:2% 3%;
        font-size: 14px;
        background-color: #a87a4fbe;
        font-family: font1;
        border-radius: 10px;        
        margin-left: 1%;
        min-width: 40px;
        width:fit-content;
        max-width: 200px;
        white-space: normal;
        text-align: left;
    }
    
    .mobilemyChat>footer {
        font-size: 5px;
        white-space: nowrap;
    }


    #mobilemessagedetail > footer {
        position:relative;
        width:100%;
        height: 100%;
    }
    .mobilechatInput {
        position:relative;
        width:80%;
        height:100%;
        border:none;
        outline: none;
        padding-top: 3%;
    }
    .mobilechatInput +button {
        width:20%;
        border:none;
        outline: none;
        background-color: #a87a4fbe;
        color:white;
        border-radius: 10px;
        height: 48px;
    }





    .mobileMessageLoading {
        position:fixed;
        top:0;
        width:100%;
        height:100%;
        background-color: rgba(211, 211, 211, 0.521);
    }
    .mobileMessageLoading>.spinner-border{
        position:fixed;
        top:45%;
        left:45%;
        
    }


    
}



</style>