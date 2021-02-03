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
                
                imgbox.style.backgroundSize='400px'
                imgbox.style.width='20%'
                imgbox.style.backgroundPosition='50% 0'
                imgbox.style.height='10%'
                imgbox.style.fontSize='15px'
                imgbox.style.left= '1%'

            } else {
                
                imgbox.style.backgroundSize='1600px'
                imgbox.style.width='60%'
                imgbox.style.backgroundPosition='50% 28%'
                imgbox.style.height='20%'
                imgbox.style.fontSize='40px'
                imgbox.style.left = '20%'
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
    width:60%;
    left: 20%;
    border-radius: 20px;
    /* background-color: #ffb5b5; */
    background-color:#3F9F47;
    background-image: url('../../assets/boardimg-main.jpg');
    text-align: center;
    font-size: 40px;
    color: rgb(255, 255, 255);
    background-size: 1600px;
    background-repeat: no-repeat;
    background-position: 50% 28%;
    transition:0.3s;
    font-family: font1;
}


#boardimg:hover{
    background-size: 200;
    background-position: 50% 35%;
}


</style>