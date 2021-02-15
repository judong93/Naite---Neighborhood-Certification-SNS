<template>
    <div id='mobilemessagestatesecond'>        
        <div v-if='!messageDetailBoolean'>

            <div class='mobilemessage2ndTitle'  >
                <h4>채팅</h4>            
            </div>
            <div class='mobilemessageList' v-for='(message,idx) in myChatList' :key="idx">
                <div class="mobilemessageListBox" @click='messageDetail(message.roomNo,message.otherNick)'>
                    <img src="../assets/cha2.png" alt="">
                    <div>
                        <span>{{message.otherNick}}</span><br>
                        <div class='mobilemessageListList'>

                            <div>{{message.lastMessage}}</div>
                            <div>{{message.lastMessageTime}}</div>
                        </div>
                    </div>
                </div>


            </div>
        </div>
        <MobileMessageDetail :otherNick = 'otherNick' :roomNo = 'roomNo' @backtosecondstate='backtosecondstate' v-else/>
    </div>
</template>
<script>
import axios from 'axios'
import MobileMessageDetail from '@/components/MobileMessageDetail'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'


export default {
    name:'MobileMessage',
    data:function(){
        return {
            myChatList:[],
            messageDetailBoolean:false,
            roomNo:0,
            otherNick:'',
        }
    },
    components:{
        MobileMessageDetail
    },
    methods:{
        backtosecondstate:function(){
            this.messageDetailBoolean = !this.messageDetailBoolean
        },
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
            this.messageDetailBoolean = !this.messageDetailBoolean
            this.roomNo = roomNo
            this.otherNick = otherNick
            

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
#mobilemessagestatesecond{
    display:none;
}
@media screen and (max-width:501px) {
    #mobilemessagestatesecond {
        display:block;
        position:relative;
        width:100%;
        height:86%;
        overflow: auto;
        overflow-x: hidden;
        font-family: font1;
        padding:5px 0;
        border: 1px solid #3F9F47;
        border-radius: 0px;        
        top:7%;
    }
    .mobilemessage2ndTitle {
        padding: 0 10px;
        display:flex;
        justify-content: space-between;
        border-bottom: 3px solid #3F9F47;
    }

    .mobilemessage2ndTitle > i {
        margin-top: 2%;
    }

    .mobilemessageListBox {
        position:relative;
        width: 100%;
        background-color: transparent;
        transition:0.3s;
        border-bottom: 1px solid rgb(138, 138, 138);
        display:flex;
        padding:2%;
    }
    .mobilemessageListBox > img {
        margin-right: 2%;
        width: 15%;
        height:10%;
        border-radius: 20%;
        border: 1px solid rgb(156, 156, 156);
    }

    .mobilemessageListBox:hover{
        box-shadow: 5px 5px 5px;
    }

    .mobilemessageListBox > div {
        text-align: left;
    }

    .mobilemessageListList{
        display: flex;
        justify-content: space-between;
        height:60%;
        width: 100%;
        
        
    }

    .mobilemessageListList :nth-child(1) {    
        width: 80%;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;    
        margin-top: auto;
        
        
    }

    .mobilemessageListList :nth-child(2){
        font-size: 12px;
        margin-top: auto;        
        white-space: nowrap;        
    }
    
}
</style>