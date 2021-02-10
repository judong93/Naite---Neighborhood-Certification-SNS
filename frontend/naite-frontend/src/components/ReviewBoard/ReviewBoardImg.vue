<template>
    <div id="reviewboardimg">
        <img :src='imgsrc' alt='?'>
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
    props:{        
        imgsrc:[Number,String],
    },
    watch:{

    },

}
</script>

<style>
#reviewboardimg {
    position: absolute;
    top:13%;
    height: 10%;
    width:60%;
    left: 20%;
    border-radius: 20px;
    text-align: center;
    font-size: 40px;
    background-size: 1600px;
    background-repeat: no-repeat;
    background-position: 50% 28%;
    transition:0.3s;
    font-family: font1;
    color:white;
    overflow:hidden;
}
</style>