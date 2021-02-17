<template>
    <div id="message" >
        <div v-if='firstmessage'  @click='changeWindow'><i class="far fa-comment-dots"></i></div>
        <MessageStateSecond v-else-if='secondmessage' @messageDetail='messageDetail' @closeSecond='closeSecond' />
        <MessageDetail :roomNo='roomNo' :otherNick='otherNick' @backtosecondstate='backtosecondstate' v-else />
        <div class='newMessageAlter'>
            <!-- <i class="far fa-envelope-open" style='font-size:15px;color:white'></i> -->
            <span>새로운 메세지가 도착했어요!</span>

        </div>
        
    </div>
    
</template>
<script>
import MessageStateSecond from './MessageStateSecond'
import MessageDetail from './MessageDetail'
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'



export default {
    name:'Message',
    components:{   
        MessageStateSecond,
        MessageDetail,
    },
    props:{
        directMessageRoomNo:[Object,String],
    },
    data: function() {
        return {
            firstmessage: true,
            secondmessage: false,
            thirdmessage: false,
            test: false,
            roomNo:0,
            otherNick:'',
            lastList:[],
            newMessage:false,
            finalMessage:[],

        }
    },
    methods:{
        backtosecondstate:function(){
            this.firstmessage = true
            this.secondmessage = false
            this.thirdmessage = false
            this.test = false
            this.changeWindow()
        },
        messageDetail:function(roomNo,otherNick){
            this.changeWindow()
            this.roomNo = roomNo
            this.otherNick = otherNick

        },
        closeSecond:function(){
            this.testback()
        },
        changeWindow:function(){
            const messageWindow = document.getElementById('message')
            if (this.newMessage === true){
                this.newMessage = false
            }
            if (this.firstmessage&& !this.test) {
                messageWindow.style.width='18%'
                messageWindow.style.height='70%'
                messageWindow.style.borderRadius='10px'
                messageWindow.style.transform='none'
                messageWindow.style.right = '1%'
                messageWindow.style.bottom='5vh'
                this.firstmessage=false
                this.secondmessage=true                
            } else if(this.secondmessage){
                messageWindow.style.width='65%'
                messageWindow.style.height='65%'
                messageWindow.style.borderRadius='10px'
                messageWindow.style.right='50%'
                messageWindow.style.bottom='50%'
                messageWindow.style.transform='translate(50%,50%)'                
                this.secondmessage=false
                this.thirdmessage=true
            }
            this.test=false
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
        testback:function(){
            const messageWindow = document.getElementById('message')
            messageWindow.style.width='100px'
            messageWindow.style.height='100px'
            messageWindow.style.borderRadius='100%'
            messageWindow.style.transform='none'
            messageWindow.style.right='5vh'
            messageWindow.style.bottom='5vh'
            this.firstmessage=true
            this.secondmessage=false
            this.thirdmessage=false
            this.test=true
            
        },
        renderChatList:function(){
            axios.get(`${SERVER_URL}/chat/room`,this.setToken())
                .then(res => {
                    if (!this.lastList){
                        this.lastList = res.data
                        return ;
                    }
                    if (this.lastList.length>0&&(this.lastList[0].roomNo != res.data[0].roomNo||this.lastList[0].lastMessage!==res.data[0].lastMessage)){
                        this.newMessage = true
                        this.finalMessage = this.lastList
                        this.lastList = res.data


                    } else {
                        this.lastList = res.data
                    }
                })
                .catch(err => {
                    console.log(err)
                })
        },
    },
    watch:{
        directMessageRoomNo:function(){
            this.roomNo = this.directMessageRoomNo.roomNo
            this.otherNick = this.directMessageRoomNo.userNick
            this.firstmessage=false
            this.secondmessage=true
            this.thirdmessage = false
            this.changeWindow()
        },
        newMessage:function(){
            const alertMessage = document.querySelector('.newMessageAlter')
            const alertMessageSpan =document.querySelector('.newMessageAlter > span')
            const messageColor = document.querySelector('#message > div:nth-child(1)')
            
            if (this.newMessage&&this.firstmessage){
                alertMessage.style.backgroundColor='lightpink'  
                alertMessage.style.width='200px'
                alertMessageSpan.style.display='initial'
                messageColor.style.color = 'lightgreen'
            } else {
                alertMessage.style.backgroundColor='transparent'  
                alertMessage.style.width='0px'
                alertMessageSpan.style.display='none'
                messageColor.style.color = 'lightcoral'
            }
        }
    },
    created(){
        setInterval(() => {
            this.renderChatList()
        }, 10000);
        
    }

    
}
</script>

<style>
#message {
    position:fixed;
    bottom: 5%;
    right: 5vh;
    width: 100px;
    height: 100px;
    
    /* background-color: red; */
    /* border: black solid 1px; */
    border-radius: 100%;
    background-color: transparent;
    /* background-color: #9B8281; */
    /* background-color: #75D701; */
    transition: 0.3s;
    cursor: pointer;
    
}
#message > div > i{
    font-size: 50px;
    margin-top: 20px;
    color:lightcoral;
    transition:0.3s;
}

#message > div >i:hover {
    font-size: 60px;
    color:lightgreen;
}
.newMessageAlter{
    top:50%;
    left:0;
    transform:translate(-100%,-50%);
    position: absolute;
    white-space: nowrap;
    font-family: font1;
    transition:0.3s;
    background-color: lightpink;
    background-color: transparent;
    padding:5px;
    border-radius: 10px;
    color:white;
    font-size: 15px;
    width:200px;    
    width:0px;    
}
.newMessageAlter>span{
    display:none
}


@media screen and (max-width:501px) {
    #message{
        display: none;
    }
    
}

</style>