<template>
    <div id="message"  @click='changeWindow'>
        <div v-if='firstmessage'>icon</div>
        <div v-else-if='secondmessage'>message목록</div>
        <div v-else>dm창</div>
        
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

        }
    },
    methods:{
        changeWindow:function(){
            const messageWindow = document.getElementById('message')
            if (this.firstmessage) {
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
        }
    },
    computed: {

    },
    
    
    created:{
        
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
    border: black solid 1px;
    border-radius: 100%;
    background-color: transparent;
    transition: 0.3s;
    
}


</style>