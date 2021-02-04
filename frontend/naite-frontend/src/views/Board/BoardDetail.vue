<template>
    <div id="boarddetail">
        <BoardListDetail :apiData='apiData'/>
    </div>
    
</template>
<script>
import BoardListDetail from '@/components/Board/BoardListDetail'
import axios from 'axios'

// const SERVER_URL = 'http://localhost:8080'
const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'


export default {
    name:'BoardDetail',
    components:{
        BoardListDetail
            
    },
    data: function() {
        return {
            apiData:{},
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
        axios.get(`${SERVER_URL}/board/list/detail/${boardNo}`)
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
#boarddetail {
    position:relative;
    width: 1920px;
    height:969px;
    overflow:hidden;
    background-color: rgb(250, 246, 240);
    
}
</style>