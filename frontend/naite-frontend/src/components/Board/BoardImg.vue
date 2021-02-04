<template>
    <div id="boardimg">
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
        message:String,
        imgsrc:String,
    },
    watch:{
        
    },
}
</script>

<style>
#boardimg {
    position: absolute;
    top:13%;
    height: 10%;
    width:60%;
    left: 20%;
    border-radius: 20px;
    /* background-image: url('../../assets/boardimg-main.jpg'); */
    background-color:#3F9F47;
    text-align: center;
    font-size: 40px;
    /* color: rgb(255, 255, 255); */

    background-size: 1600px;
    background-repeat: no-repeat;
    background-position: 50% 28%;
    transition:0.3s;
    font-family: font1;
    color:white;
}


#boardimg:hover{
    background-size: 200;
    background-position: 50% 35%;
}


</style>