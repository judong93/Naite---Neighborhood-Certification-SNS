<template>
    <div id="maincategory">
        <BoardImg :message='message' :imgsrc='imgsrc'/>
        <BoardList :bigCategoryNo='bigCategoryNo'/>
    </div>
    
</template>
<script>

import BoardImg from '@/components/Board/BoardImg'
import BoardList from '@/components/Board/BoardList'

export default {
    name:'MainCategory',
    components:{
        BoardImg,
        BoardList,
    },
    data: function() {
        return {
            bigCategoryNo:0,
            message:'당신이 모르는 동네이야기',
            imgsrc: 'boardimg-main.png',
            onDetail: false,
            dumydata: ['#','번화가 번화가는 자유게시판','동사무소 동사무소는 질문게시판 질문해보세용',],
            boardNo: 0,
            
        }
    },
    methods:{
        renderingList:function(){
            this.bigCategoryNo = this.$route.params.bigCategoryNo
            console.log(this.bigCategoryNo,'라우트변경')
            this.message = this.dumydata[this.bigCategoryNo]

        },
        t:function() {
            this.$router.push({name:'TestPosting'})
        }

        
    },
    computed: {

    },
    watch: {
        $route(to,from){
            if (to.path !== from.path){
                this.renderingList()
            }
        }
    },
    created(){        
        this.renderingList()
    } 
}
</script>

<style>
#maincategory {
    position:relative;
    width: 1920px;
    height:969px;
    overflow:hidden;
    background-color: rgb(250, 246, 240);
}

</style>