<template>
    <div id="main">
        <Navbar />
        <BoardImg :messagetitle='messagetitle' :message='message' :imgsrc='imgsrc' :onDetail='onDetail'/>
        <BoardDetail :onDetail='onDetail'/>
        <BoardList :onDetail='onDetail'/>
        <CategoryBar />



        <button class="toDetail" @click='toDetail'>디테일보기</button>
        <button class="backdetail" @click='backdetail'>돌아가기</button>
        <Message />
    </div>
    
</template>
<script>
import Navbar from '@/components/Navbar'
import Message from '@/components/Message'
import BoardImg from '@/components/BoardImg'
import BoardDetail from '@/components/BoardDetail'
import BoardList from '@/components/BoardList'
import CategoryBar from '@/components/CategoryBar'

export default {
    name:'Main',
    components:{
        Navbar,
        Message,
        BoardImg,
        BoardDetail,
        BoardList,
        CategoryBar,
    },
    data: function() {
        return {
            messagetitle:'나의 이웃테두리: 나이테',
            message:'당신이 모르는 동네이야기',
            imgsrc: 'boardimg-main.png',
            onDetail: false,
            
        }
    },
    methods:{
        toDetail:function(){
            this.onDetail=true
            
        },
        backdetail:function(){
            this.onDetail=false
        },

        
    },
    computed: {

    },

    
}
</script>

<style>
#main {
    position:relative;
    height: 100vh;
    width:100vw;
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
</style>