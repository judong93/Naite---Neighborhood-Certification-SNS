<template>
    <div id="testposting">
        <PostingBoard @createForm='createForm' />
    </div>
</template>

<script>
import PostingBoard from '@/components/PostingBoard'
import axios from 'axios'

// const SERVER_URL = 'http://localhost:8080'
const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'

export default {
    name:'TestPosting',
    components:{
        PostingBoard,
    },
    methods:{
        createForm:function(params,smallCategoryNo,reviewStar,marketCost)
        {
            if (params.bigCategoryNo === '3') {
                const param = {
                    'board':params,
                    'reviewStar':reviewStar,
                    'samllCategoryNo':smallCategoryNo
                }
                axios.post(`${SERVER_URL}/review/insert`,param,this.setToken())
                    .then(res=>{
                        console.log(res)
                    })
                    .catch(err=> {
                        err.data
                        localStorage.removeItem('jwt')
                        alert('사용시간이 지나 로그아웃되었습니다! 재접속 해주세요')
                        this.$router.push({name:'Sign'})
                        })
            } else if (params.bigCategoryNo ==='5'){
                const param = {
                    'board':params,
                    'marketCost':marketCost,
                    'smallCategoryNo':smallCategoryNo,
                }
                axios.post(`${SERVER_URL}/market/insert`,param,this.setToken())
                    .then(res=> {
                        console.log(res)
                    })
                    .catch(err => {
                        err.data
                        localStorage.removeItem('jwt')
                        alert('사용시간이 지나 로그아웃되었습니다! 재접속 해주세요')
                        this.$router.push({name:'Sign'})
                    })
            } else {                
                axios.post(`${SERVER_URL}/board/insert`,params,this.setToken())
                    .then(res => {
                        console.log(res)
                        
                        this.$router.push({name:'Board',params:{bigCategoryNo:params.bigCategoryNo}})
                    })
                    .catch(err=>{
                        err.data
                        localStorage.removeItem('jwt')
                        alert('사용시간이 지나 로그아웃되었습니다! 재접속 해주세요')
                        this.$router.push({name:'Sign'})
                    })
            }
        },
        setToken:function(){
            const token=localStorage.getItem('jwt')
            const config = {
                headers: {
                'auth-token':`${token}`
                }
            }
            console.log(config)
            return config

            
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