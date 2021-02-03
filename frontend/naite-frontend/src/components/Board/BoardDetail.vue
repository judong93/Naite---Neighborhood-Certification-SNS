<template>
    <div id="boarddetail">
        {{apiData}}
    </div>
    
</template>
<script>
import axios from 'axios'

const SERVER_URL = 'http://localhost:8080'


export default {
    name:'BoardDetail',
    components:{
                
    },
    data: function() {
        return {
            apiData:{},
        }
    },
    props:{
        onDetail:Boolean,
        boardNo: Number,
    },
    methods:{

        
    },
    computed: {

    },
    watch:{
        onDetail: function(){
            const detailWindow = document.getElementById('boarddetail')
            if (this.onDetail){
                
                detailWindow.style.right='10%'
            } else {
                detailWindow.style.right='-100%'
            }
        },
        boardNo: function(){
            axios.get(`${SERVER_URL}/board/list/detail/${this.boardNo}`)
                .then(res => {
                    this.apiData = res.data
                })
                .catch(err => {
                    console.log(err)
                })

        }
    },

    
}
</script>

<style>
#boarddetail {
    position:absolute;
    width: 68%;
    height: 90%;
    background-color: rgba(236, 189, 189,0.5);
    top: 10%;
    right:-100%;
    
    transition:0.3s;
    
}
</style>