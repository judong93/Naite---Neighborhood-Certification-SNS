<template>
    <div id="message" >
        <div v-if='firstmessage'  @click='changeWindow'><i class="far fa-comment-dots"></i></div>
        <MessageStateSecond v-else-if='secondmessage' @messageDetail='messageDetail' @closeSecond='closeSecond' />
        <MessageDetail :roomNo='roomNo' :otherNick='otherNick' @backtosecondstate='backtosecondstate' v-else />
       
        
    </div>
    
</template>
<script>
import MessageStateSecond from './MessageStateSecond'
import MessageDetail from './MessageDetail'


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
            
        }

    },
    watch:{
        directMessageRoomNo:function(){
            this.roomNo = this.directMessageRoomNo.roomNo
            this.otherNick = this.directMessageRoomNo.userNick
            this.firstmessage=false
            this.secondmessage=true
            this.thirdmessage = false
            this.changeWindow()
        }
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


@media screen and (max-width:501px) {
    #message{
        display: none;
    }
    
}

</style>