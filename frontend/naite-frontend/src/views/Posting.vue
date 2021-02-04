<template>
    <div id="testposting">
        <PostingBoard @createForm='createForm' />
    </div>
</template>

<script>
import PostingBoard from '@/components/PostingBoard'
import axios from 'axios'

const SERVER_URL = 'http://localhost:8080'

export default {
    name:'TestPosting',
    components:{
        PostingBoard,
    },
    methods:{
        createForm:function(params){
            axios.post(`${SERVER_URL}/board/insert`,params)
                .then(res=>{
                    console.log(res)
                    this.$router.push({name:'MainCategoty', params:{bigCategoryNo:params.bigCategoryNo}})
                })
                .catch(err=>{
                    console.log(err)
                })
        }
    }

}
</script>

<style>
#testposting {
    position:relative;
    width: 1920px;
    height:969px;
    overflow:hidden;
    background-color: rgb(250, 246, 240);
    font-family: font1;
}


</style>