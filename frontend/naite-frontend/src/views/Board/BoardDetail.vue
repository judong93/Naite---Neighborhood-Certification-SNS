<template>
    <div id="boarddetail">
        <BoardListDetail :apiData='apiData' :boardNo='boardNo' :updateCommentCntCheck='updateCommentCntCheck' @sendMessageDirect='sendMessageDirect' />
        <BoardComment :bigCategoryNo='bigCategoryNo' @updateCommentCnt='updateCommentCnt' @sendMessageDirect='sendMessageDirect'  />
    </div>
    
</template>
<script>
import BoardListDetail from '@/components/Board/BoardListDetail'
import BoardComment from '@/components/Board/BoardComment'

import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'

export default {
    name:'BoardDetail',
    components:{
        BoardListDetail,
        BoardComment,
            
    },
    data: function() {
        return {
            apiData:{},
            boardNo:0,
            bigCategoryNo:0,
            updateCommentCntCheck:0,
        }
    },
    props:{
    },
    methods:{
        updateCommentCnt:function(){
            
            this.updateCommentCntCheck+=1
        },
        sendMessageDirect:function(roomNo,userNick,userPic,userNo){
            if (screen.width < 501) {
                this.$router.push({name:'MobileMessage',params:{'roomNo':roomNo,'otherNick':userNick}})
            } else {
                this.$emit('sendMessageDirect',roomNo,userNick,userPic,userNo)                    
            }
        }
        
    },
    computed: {

    },
    watch:{
        
    },
    created(){
        const boardNo = this.$route.params.boardNo
        this.boardNo = boardNo
        
        axios.get(`${SERVER_URL}/board/list/detail/${boardNo}`)
            .then(res => {
                this.apiData = res.data 
                this.bigCategoryNo = this.apiData.bigCategoryNo
            })
            .catch(err=>{
                console.log(err)
            })
        
    }

    
}
</script>

<style>
#boarddetail {
    position:relative;
    width: 1920px;
    height:969px;
    overflow:auto;
    background-color: rgb(250, 246, 240);
    
}

@media screen and (max-width:501px) {
    #boarddetail {
        width: 100vw;
        height:100vh;
        overflow-x: hidden;
    }
    
}
</style>