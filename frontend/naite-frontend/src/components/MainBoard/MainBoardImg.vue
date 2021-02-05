<template>
    <div id="mainboardimg">
        
    </div>
    
</template>
<script>


export default {
    name:'MainBoardImge',
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
    },
    watch:{
        
    },
    
}
</script>

<style>
#mainboardimg {
    position: absolute;
    top:13%;
    height: 20%;
    width:60%;
    left: 20%;
    border-radius: 20px;
    background-image: url('../../assets/boardimg-main.jpg');
    text-align: center;
    font-size: 40px;
    color: rgb(73, 69, 69);
    background-repeat: no-repeat;
    background-position: 50% 85%;
    transition:0.3s;
    font-family: font1;
}


</style>