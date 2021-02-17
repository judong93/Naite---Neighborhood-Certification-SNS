<template>
    <div id='messagestatesecond'>
        <div class='message2ndTitle'>
            <h4>채팅목록</h4>
            <i class="fas fa-times-circle" @click='closeSecond'></i>
        </div>
        <div class='messageList' v-for='(message,idx) in myChatList' :key="idx">
            <div class="messageListBox" @click='messageDetail(message.roomNo,message.otherNick)'>
                <img :src='message.otherPic' alt="">
                <div>
                    <span>{{message.otherNick}}</span><br>
                    <div class='messageListList'>

                        <div>
                            {{message.lastMessage ? message.lastMessage: `${message.otherNick}님과의 채팅방이 개설되었습니다!` }}
                        </div>
                        <div>
                            {{message.lastMessageTime}}
                        </div>
                    </div>
                </div>
            </div>


        </div>
    </div>
</template>
<script>
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'


export default {
    name:'MessageStateSecond',
    data:function(){
        return {
            myChatList:[],
        }
    },
    methods:{
        renderChatList:function(){
            axios.get(`${SERVER_URL}/chat/room`,this.setToken())
                .then(res => {
                    this.myChatList = res.data                    
                })
                .catch(err => {
                    console.log(err)
                })
        },
        messageDetail:function(roomNo,otherNick){
            this.$emit('messageDetail',roomNo,otherNick)

        },
        closeSecond:function(){
            this.$emit('closeSecond')
        },
        setToken:function(){
            const token = localStorage.getItem('jwt')
            const config = {
                headers: {
                'auth-token':`${token}`
                }
            }        
            return config 
        }

    },
    created(){
        this.renderChatList()
    }
}
</script>

<style>
#messagestatesecond {
    position:relative;
    width:100%;
    height:100%;
    overflow: auto;
    overflow-x: hidden;
    background-color: white;
    font-family: font1;
    padding:0;
    border: 1px solid #3F9F47;
    border-radius: 10px;
}
.message2ndTitle {    
    
    padding: 0 10px;
    display:flex;
    justify-content: space-between;
    background-color: #a87a4fda;
    border-bottom: 3px solid #ffffff;
    color:white;
}

.message2ndTitle > i {
    margin-top: 2%;
}
.message2ndTitle > .fa-times-circle{
    display:block;
    position:relative;
    right:0;
    transform:none;
    color:white;
    transition:0.3s;
}
.message2ndTitle > .fa-times-circle:hover{
    color:red
}
.messageListBox {
    position:relative;
    width: 100%;
    background-color: #a7784c52;
    transition:0.3s;
    border-bottom: 1px solid rgb(248, 248, 248);
    display:flex;
    padding:2%;
    font-size: 15px;
    color:black;
}
.messageListBox > img {
    margin-right: 2%;
    width: 65px;
    height:65px;
    border-radius: 20%;
    border: 1px solid rgb(156, 156, 156);
}

.messageListBox:hover{
    box-shadow: 5px 5px 5px;
}

.messageListBox > div {
    text-align: left;
}

.messageListList{    
    height:60%;
    font-size: 12px;
    
}

.messageListList :nth-child(1) {    
    width: 250px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;    
    margin-top: auto;
    
    
}

.messageListList :nth-child(2){
    font-size: 12px;
    margin-top: auto;
}




</style>