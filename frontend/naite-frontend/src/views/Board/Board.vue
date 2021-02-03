<template>
    <div id="maincategory">
       
        <BoardImg :messagetitle='messagetitle' :message='message' :imgsrc='imgsrc' :onDetail='onDetail'/>
        <BoardDetail :onDetail='onDetail' :boardNo='boardNo' />
        <BoardList :onDetail='onDetail' :bigCategoryNo='bigCategoryNo' @onDetailMethod='onDetailMethod' />
        <CategoryBar />
        <button class="toDetail" @click='toDetail'>디테일보기</button>
        <button class="backdetail" @click='backdetail'>돌아가기</button>
        <button @click='t' style='position:absolute;z-index:100'>tesast</button>
    </div>
    
</template>
<script>

import BoardImg from '@/components/Board/BoardImg'
import BoardDetail from '@/components/Board/BoardDetail'
import BoardList from '@/components/Board/BoardList'
import CategoryBar from '@/components/CategoryBar'

export default {
    name:'MainCategory',
    components:{
        BoardImg,
        BoardDetail,
        BoardList,
        CategoryBar,
    },
    data: function() {
        return {
            bigCategoryNo:0,
            messagetitle:'나의 이웃테두리: 나이테',
            message:'당신이 모르는 동네이야기',
            imgsrc: 'boardimg-main.png',
            onDetail: false,
            dumydata: ['#','번화가 번화가는 자유게시판','동사무소 동사무소는 질문게시판 질문해보세용',],
            boardNo: 0,
            
        }
    },
    methods:{
        toDetail:function(){
            if (this.onDetail) {
                this.onDetail = false
            } else {
                this.onDetail = true
            }
            
        },
        backdetail:function(){
            this.onDetail=false
        },
        onDetailMethod:function(n){
            this.toDetail()
            this.boardNo = n
        },
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

.toDetail{
    /* 임시로 위치지정 */
    position: fixed;
    bottom:10%;
    left:0%;
}
.backdetail {
    /* 임시위치지정 */
    position:fixed;
    bottom:5%;
    left:0%;
}

.backdetail + button {
    position:fixed;
    bottom: 0%;
    left: 0%;
}

</style>