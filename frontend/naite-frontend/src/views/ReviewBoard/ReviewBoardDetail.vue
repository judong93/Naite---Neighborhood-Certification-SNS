<template>
    <div id="boarddetail">
        <ReviewBoardListDetail :apiData='apiData' :boardNo='boardNo' :updateCommentCntCheck='updateCommentCntCheck' @sendMessageDirect='sendMessageDirect' />
        <BoardComment :bigCategoryNo='bigCategoryNo' @updateCommentCnt='updateCommentCnt' />
    </div>
    
</template>
<script>
import ReviewBoardListDetail from '@/components/ReviewBoard/ReviewBoardListDetail'
import BoardComment from '@/components/Board/BoardComment'

import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'

export default {
    name:'ReviewBoardDetail',
    components:{
        ReviewBoardListDetail,
        BoardComment,
            
    },
    data: function() {
        return {
            apiData:{'board':{}},            
            bigCategoryNo:3,
            reviewNo:0, 
            updateCommentCntCheck:0,
            boardNo:0,
        }
    },
    props:{
    },
    methods:{
        updateCommentCnt:function(){
            this.updateCommentCntCheck+=1
        },
        setToken:function(){
            const token = localStorage.getItem('jwt')
            const config = {
                headers: {
                'auth-token':`${token}`
                }
                }        
            return config 
        },
        sendMessageDirect:function(roomNo,userNick){
            this.$emit('sendMessageDirect',roomNo,userNick)
        }
        
    },
    computed: {

    },
    watch:{
        
    },
    created(){
        const reviewNo = this.$route.params.reviewNo    
        this.boardNo = this.$route.params.boardNo    
        this.reviewNo = reviewNo
        axios.get(`${SERVER_URL}/review/detail/${reviewNo}`)
            .then(res => {
                this.apiData = res.data                 
            })
            .catch(err=>{
                console.log(err)
            })
        
    }

    
}
</script>

<style>
#reviewboarddetail {
    position:relative;
    width: 1920px;
    height:969px;
    overflow:auto;
    background-color: rgb(250, 246, 240);
    
}

@media screen and (max-width:501px) {
    #reviewboarddetail {
        width: 100vw;
        heigt:100vh;
    }
    
}
</style>