<template>
    <div id="mainboardlist">
        
        <div class='mainboardBack' >
            <div class="mainBoardFree" v-for='(no,idx) in boardNo' :key="idx">
                <div class="mainBoardHead">
                    <div class='leftPin'></div>
                    <div class='mainBoardHeadTitle'>{{boardTitle[idx]}}</div>
                    <div class='rightPin'></div>
                </div>
                <hr>
                <div class="mainBoardBody" v-for='(data,idx2) in apiData[`${idx}`]' :key='idx2'>
                    <img src="../../assets/cha2.png" alt="" width='30px' height="30px">                    
                    <p @click='toDetail(data.boardNo)' style='cursor:pointer'>{{data.boardTitle}}</p>
                    <div @click='toDetail(data.boardNo)' style='cursor:pointer' class='mainBoardStatus'>
                        <i class="far fa-thumbs-up"></i>
                        {{data.boardLikeCnt}}
                        <i class="far fa-comment-dots"></i>
                        {{data.boardCommentCnt}}
                        <br>
                        <i class="far fa-clock"></i>
                        {{data.boardCreatedAt}}
                        
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'


export default {
    name:'MainBoardList',
    data: function(){
        return {
            boardNo:[1,2,4],
            boardTitle:['번화가소식','동사무소소식','소리소문'],
            apiData: {
                '0': {},
                '1': {},
                '2': {},
            },
            // commentCnt:0,
        }
    },
    methods:{
        toDetail:function(num){
            this.$router.push({name:'BoardDetail',params:{boardNo:num}})
        },
        setToken:function(){
            const token=localStorage.getItem('jwt')
            const config = {
                headers: {
                'auth-token':`${token}`
                }
            }
            return config 
        },
    },
    computed:{
    },
    created(){
        for (let i=0;i<3;i++){
            console.log(this.setToken())
            axios.get(`${SERVER_URL}/board/list/top/${this.boardNo[i]}`,this.setToken())
                .then(res => {
                    this.apiData[`${i}`] = res.data               
                })  
                .catch(err=>{
                    console.log(err)
                })
            }       
    }
    

}
</script>
<style>
#mainboardlist {
    position:absolute;
    top: 36%;
    left: 20%;
    width: 60%;
    height:62%;
    overflow:auto;
    font-family: font1;
}


.mainboardBack {
    position: absolute;
    top: 50%;
    left: 50%;
    width: 100%;
    height: 100%;
    background-color:#3F9F47;
    transform: translate(-50%,-50%);
    border-top: 20px solid  #d69960;
    border-bottom: 20px solid #d69960;
    border-left: 20px solid #A87A4F;
    border-right: 20px solid  #A87A4F;
    display:flex;
}

.mainboardBack > div {
    position:relative;
    border-radius: 10px;
    width: 30%;
    height: 90%;    
    margin: auto;
    background-color: white;
    font-size: 15px;
}
.mainBoardHead {
    display:flex;
    justify-content: space-between;
    
}

.mainBoardHeadTitle {
    margin-top: 10px;
}

.mainBoardHead + hr {
    width: 100%;
}

.leftPin, .rightPin {
    width: 10px;
    height: 10px;
    background-color: black;
    border-radius: 10px;
}

.mainBoardBody {
    display: flex;
    justify-content: space-between;
    margin: 20px 10px;
    padding-bottom: 10px;
    border-bottom: 1px solid black;
    
}

.mainBoardBody > p {
    text-align: left;
    width:200px;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}

.mainBoardStatus {
    text-align: right;
    font-size: 10px;
    width:170px;
}

@media screen and (max-width:501px) {
    #mainboardlist {
        position:absolute;
        top: 8%;
        left: 0%;
        width: 100%;
        height:83%;        
    }


    .mainboardBack {        
        display:block;
        height:100%;
        overflow: auto;
    }

    .mainboardBack > div {
        position:relative;
        border-radius: 10px;
        width: 100%;
        max-height: 100%;    
        margin: auto;
        margin-top: 5%;
        
        
    }
   
    
}

</style>