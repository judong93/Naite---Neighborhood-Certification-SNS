<template>
    <div id="boarddetail">
        <BoardListDetail :apiData='apiData' :boardNo='boardNo'/>
        <BoardComment />
    </div>
    
</template>
<script>
import BoardListDetail from '@/components/Board/BoardListDetail'
import BoardComment from '@/components/Board/BoardComment'

import axios from 'axios'

const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'


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
        }
    },
    props:{
    },
    methods:{

        
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
                console.log(res.data)
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
</style>