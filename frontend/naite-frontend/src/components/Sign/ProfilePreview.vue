<template>
    <div v-if='!beforeSelectPic' style='position:relative'>
        <div id='preview'>
            <img :src="image" alt="" width='150px' height="150px">  
        </div>
        <div id='mobilePreview'>
            <img :src="image" alt="" width='100px' height="100px">  
        </div>
        <button @click='resettingPic'>프로필사진 재설정</button>
    </div>
</template>

<script>
export default {
    name:'ProfilePreview',
    data:function(){
        return {
            image:'',
        }
    },
    props:{
        profileImg:[Array,String,File],
        beforeSelectPic:Boolean,

    },
    methods:{
        resettingPic:function(){            
            this.$emit('resettingPic')
        }
    },  
    watch:{
        profileImg: function(){
            if (!this.profileImg){
                return ;
            }
            // var image = new Image()
            var reader = new FileReader()
            let that = this
            reader.onload=(e)=>{
                that.image = e.target.result
            }
            reader.readAsDataURL(this.profileImg)
        }
    }
}
</script>

<style>
#mobilePreview{
    display: none;
}
#preview {
    position: relative;
    display:block;
    width: 150px;
    height:150px;    
    border-radius: 100%;
    overflow: hidden;
    margin-top: 20px;
    left:50%;
    transform:translateX(-50%)
    

}


#mobilePreview + button {
    position:relative;
    display:block;
    font-size: 15px;
    margin-top: 20px;
    height: 40px;
    width:32%;
    text-align:center ;
    left:50%;
    color:white;
    transform:translateX(-50%);
    border:none;
    border-radius: 10px;
    background-color: rgb(243, 133, 43);

}
@media screen and (max-width: 501px) {
    #preview {
        display: none;
    }
    #mobilePreview {
        position:relative;
        display: block;
        width: 100px;
        height:100px;
        border-radius: 100%;
        overflow: hidden;
        margin:0;
        left:35%;
        /* transform:translateX(-50%) */
    }
    #mobilePreview + button {
        
        position:relative;
        font-size: 15px;
        height: 5%;
        margin-left:0%;
        margin-top: 0%;
        width:100%;
        transform: translateX(-50%);
        color:black;
        border:none;
        border-radius: 10px;
        background-color: rgb(247, 247, 247);
        top:50%;
        left: 50%;

    }

}

</style>