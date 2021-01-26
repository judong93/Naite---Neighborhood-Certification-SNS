<template>
    <div id="message"  @click='changeWindow'>
        <div v-if='firstmessage'>icon</div>
        <div v-else-if='secondmessage'>message목록</div>
        <div v-else>dm창</div>
        <button class="test" @click='testback'>test:backtofirstmessage</button>
    </div>
    
</template>
<script>

export default {
    name:'Message',
    components:{                
    },
    data: function() {
        return {
            firstmessage: true,
            secondmessage: false,
            thirdmessage: false,
            test: false,

        }
    },
    methods:{
        changeWindow:function(){
            const messageWindow = document.getElementById('message')
            
            if (this.firstmessage&& !this.test) {
                messageWindow.style.width='250px'
                messageWindow.style.height='500px'
                messageWindow.style.borderRadius='10px'
                this.firstmessage=false
                this.secondmessage=true
                console.log('?')
            } else if(this.secondmessage){
                messageWindow.style.width='1300px'
                messageWindow.style.height='600px'
                messageWindow.style.borderRadius='10px'
                messageWindow.style.right='300px'
                messageWindow.style.bottom='200px'
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
            messageWindow.style.right='5vh'
            messageWindow.style.bottom='5vh'
            this.firstmessage=true
            this.secondmessage=false
            this.thirdmessage=false
            this.test=true
            
        }

    },
    computed: {

    },
    
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
    border: black solid 1px;
    border-radius: 100%;
    background-color: rgb(107, 107, 189);
    transition: 0.3s;
    
}


</style>