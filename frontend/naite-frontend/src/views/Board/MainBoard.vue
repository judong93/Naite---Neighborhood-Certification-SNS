<template>
    <div id="main">
        <MainBoardImg 
        :messagetitle='messagetitle' 
        :message='message'
        :imgsrc='imgsrc'
        />
        <MainBoardList />
    </div>
    
</template>
<script>
import MainBoardList from '@/components/MainBoard/MainBoardList'
import MainBoardImg from '@/components/MainBoard/MainBoardImg'


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

</style>