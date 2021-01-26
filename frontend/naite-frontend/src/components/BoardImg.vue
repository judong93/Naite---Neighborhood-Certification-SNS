<template>
    <div id="boardimg">
        <span>{{messagetitle}}</span><br>
        <span>{{message}}</span>
    </div>
    
</template>
<script>


export default {
    name:'BoardImge',
    components:{
                
    },
    data: function() {
        return {
        }
    },
    methods:{
    },
    computed: {

    },
    props:{
        messagetitle:String,
        message:String,
        imgsrc:String,
        onDetail:Boolean,
    },
    watch:{
        onDetail:function(){
            const imgbox = document.getElementById('boardimg')
            if (this.onDetail) {
                imgbox.style.margin='0 1%'
                imgbox.style.backgroundSize='400px'
                imgbox.style.width='20%'
                imgbox.style.backgroundPosition='50% 0'
                imgbox.style.height='10%'
                imgbox.style.fontSize='15px'

            } else {
                imgbox.style.margin='0 10%'
                imgbox.style.backgroundSize='1600px'
                imgbox.style.width='80%'
                imgbox.style.backgroundPosition='50% 28%'
                imgbox.style.height='20%'
                imgbox.style.fontSize='40px'
            }
        }
    },

  
    
}
</script>

<style>
#boardimg {
    position: absolute;
    top:10%;
    height: 20%;
    width:80%;
    margin: 0 10%;
    background-color: #ffb5b5;
    background-image: url('../assets/boardimg-main.png');
    text-align: center;
    font-size: 40px;
    color: white;
    background-size: 1600px;
    background-repeat: no-repeat;
    background-position: 50% 28%;
    transition:0.3s;
}


#boardimg:hover{
    background-size: 200;
    background-position: 50% 35%;
}


</style>