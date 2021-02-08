<template>
    <div id="main">

        <MainBoardImg 
        :messagetitle='messagetitle' 
        :message='message'
        :imgsrc='imgsrc'
        />
        <MainBoardList />
        <!-- <div class='bnameWelcome'>
            <span>소식</span>
        </div> -->
    </div>
    
</template>
<script>
import MainBoardList from '@/components/MainBoard/MainBoardList'
import MainBoardImg from '@/components/MainBoard/MainBoardImg'
// import jwt_decode from 'jwt-decode'

export default {
    name:'Main',
    components:{
        MainBoardList,
        MainBoardImg,
        
    },
    data: function() {
        return {
            messagetitle:'나의 이웃테두리: 나이테',
            message:'당신이 모르는 동네이야기',
            imgsrc: 'boardimg-main.png',
            
            
        }
    },
    methods:{

        
    },
    computed: {

    },
    created(){
        // const decode = jwt_decode(localStorage.getItem('jwt'))
        // console.log(decode)
    }

    
}
</script>

<style>
#main {
    position:relative;
    width: 1920px;
    height:969px;
    overflow:hidden;
    background-color: rgb(250, 246, 240);
}

.bnameWelcome {
    position: absolute;
    top: 33%;
    left: 50%;
    transform: translateX(-50%);
    width: 20%;
    height:7%;
    background-color: black;
    padding: 20px;
    color:white;
}

</style>