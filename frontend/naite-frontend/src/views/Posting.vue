<template>
    <div id="testposting">
        <PostingBoard @createForm='createForm' />
    </div>
</template>

<script>
import PostingBoard from '@/components/PostingBoard'
import axios from 'axios'

// const SERVER_URL = 'http://localhost:8080'
const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'

export default {
    name:'TestPosting',
    components:{
        PostingBoard,
    },
    methods:{
        createForm:function(params,smallCategoryNo,reviewStar,marketCost,files){
            

            if (params.bigCategoryNo === '3') {
                const param = {
                    "bigCategoryNo": params.bigCategoryNo,
                    "boardContent": params.boardContent,
                    "boardPic": params.boardPic,
                    "boardTitle": params.boardTitle,
                    'files': files,                    
                    "openFlag": params.openFlag,
                    "reviewStar":reviewStar,
                    'smallCategoryNo':smallCategoryNo,
                    "unknownFlag": params.unknownFlag,                 
                }  
                let createFormData = new FormData()
                const token = localStorage.getItem('jwt')
                const paramKey = ["bigCategoryNo", "boardContent","boardPic","boardTitle",'files','marketCost',"openFlag",'smallCategoryNo',"unknownFlag"]
                for (let i=0; i<9;i++){
                    if (i!==4){
                        createFormData.append(`${paramKey[i]}`,param[`${paramKey[i]}`])
                    } else {
                        for (let j=0;j<files.length;j++){
                            createFormData.append(`${paramKey[i]}`,files[j])
                        }
                    }
                    
                }
                axios.post(`${SERVER_URL}/review/insert`,createFormData,{
                    headers:{
                        'Content-type':'multipart/form-data',
                        'auth-token': token,
                    }
                })
                    .then(res=>{
                        console.log(res) 
                        this.$router.push({name:'ReviewBoard'})
                        
                    })
                    .catch(err=> {
                        console.log(err)
                        // localStorage.removeItem('jwt')
                        alert('해당 게시물은 현재 사용할 수 없습니다!')
                        // this.$router.push({name:'Sign'})
                        })
            } else if (params.bigCategoryNo ==='5'){
                const param = {
                    "bigCategoryNo": params.bigCategoryNo,
                    "boardContent": params.boardContent,
                    "boardPic": params.boardPic,
                    "boardTitle": params.boardTitle,
                    'files': files,
                    'marketCost':marketCost,
                    "openFlag": params.openFlag,
                    'smallCategoryNo':smallCategoryNo,
                    "unknownFlag": params.unknownFlag,                 
                }  
                let createFormData = new FormData()
                const token = localStorage.getItem('jwt')
                const paramKey = ["bigCategoryNo", "boardContent","boardPic","boardTitle",'files','marketCost',"openFlag",'smallCategoryNo',"unknownFlag"]
                for (let i=0; i<9;i++){
                    if (i!==4){
                        createFormData.append(`${paramKey[i]}`,param[`${paramKey[i]}`])
                    } else {
                        for (let j=0;j<files.length;j++){
                            createFormData.append(`${paramKey[i]}`,files[j])
                        }
                    }
                    
                }
                
                axios.post(`${SERVER_URL}/market/insert`,createFormData,{
                    headers:{
                        'Content-type':'multipart/form-data',
                        'auth-token': token,
                    }
                })
                    .then(res=> {
                        console.log(res)
                        this.$router.push({name:'MarketBoard'})
                    })
                    .catch(err => {
                        console.log(err)
                        // err.data
                        // localStorage.removeItem('jwt')
                        // alert('사용시간이 지나 로그아웃되었습니다! 재접속 해주세요')
                        // this.$router.push({name:'Sign'})
                    })
            } else {     
                const param = {
                    "bigCategoryNo": params.bigCategoryNo,
                    "boardContent": params.boardContent,
                    "boardPic": params.boardPic,
                    "boardTitle": params.boardTitle,
                    'files': files,
                    "openFlag": params.openFlag,
                    "unknownFlag": params.unknownFlag,                 
                }  
                let createFormData = new FormData()
                const token = localStorage.getItem('jwt')
                const paramKey = ["bigCategoryNo", "boardContent","boardPic","boardTitle",'files',"openFlag","unknownFlag"]
                for (let i=0; i<7;i++){
                    if (i!==4){
                        createFormData.append(`${paramKey[i]}`,param[`${paramKey[i]}`])
                    } else {
                        for (let j=0;j<files.length;j++){
                            createFormData.append(`${paramKey[i]}`,files[j])
                        }
                    }
                    
                }
                axios.post(`${SERVER_URL}/board/insert`,createFormData,{
                    headers:{
                        'Content-type':'multipart/form-data',
                        'auth-token': token,
                    }
                })
                    .then(res => {
                        console.log(res)
                        this.$router.push({name:'Board',params:{bigCategoryNo:params.bigCategoryNo}})
                    })
                    .catch(err=>{
                        err.data                        
                        console.log(err)
                        // localStorage.removeItem('jwt')
                        // alert('사용시간이 지나 로그아웃되었습니다! 재접속 해주세요')
                        // this.$router.push({name:'Sign'})
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

@media screen  and (max-width: 501px){

    #testposting {
    width: 100vw;
    height:100vh;

}
}


</style>