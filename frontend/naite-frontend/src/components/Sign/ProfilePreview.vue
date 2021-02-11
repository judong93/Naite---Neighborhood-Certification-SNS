<template>
    <div v-if='!beforeSelectPic'>
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
    visibility: hidden;
}
#preview {
    width: 150px;
    height:150px;    
    border-radius: 100%;
    overflow: hidden;
    margin-left: 150px;
}


#mobilePreview + button {

    font-size: 15px;
    height: 40px;
    margin-left: 170px;
    color:white;
    border:none;
    border-radius: 10px;
    background-color: rgb(141, 138, 138);

}
@media screen and (max-width: 501px) {
    #preview {
        display: none;
    }
    #mobilePreview {
        visibility: visible;
        width: 100px;
        height:100px;
        border-radius: 100%;
        overflow: hidden;
        margin:0 auto;
    }
    #mobilePreview + button {

        font-size: 15px;
        height: 5%;
        margin-left:50%;
        margin-top: 5%;
        transform: translateX(-50%);
        color:white;
        border:none;
        border-radius: 10px;
        background-color: rgb(212, 143, 143);

    }

}

</style>