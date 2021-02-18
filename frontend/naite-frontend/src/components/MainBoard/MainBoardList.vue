<template>
    <div id="mainboardlist">
        <div class='mobileBoardTitle'>
            <h3 >골목길</h3>
            <span>우리동네 소식모아보기</span>
        </div >
        <div class='mainboardBack' >
            <div class="mainBoardFree" v-for='(no,idx) in boardNo' :key="idx">
                <div class="mainBoardHead">
                    <div class='leftPin'></div>                   
                    <div class='mainBoardHeadTitle'>
                        {{boardTitle[idx]}} <span>{{boardSmallTitle[idx]}}</span>
                    </div>
                    <div class='rightPin'></div>
                </div>
                <hr>
                <div class="mainBoardBody" v-for='(data,idx2) in apiData[`${idx}`]' :key='idx2'>
                    <div>   
                        <img :src="data.unknownFlag? basicImg:data.userPic" alt="">                    
                    </div>
                    <div @click='toDetail(data.boardNo)' style='cursor:pointer'>
                        <div >
                            {{data.boardTitle}}
                        </div>
                        <div>
                            
                            <i class="fas fa-user-alt" ></i>
                            {{data.unknownFlag? '익명':data.userNick}}
                            <i class="far fa-images" style='margin-left:5px'></i>
                            {{data.files.length}}
                            <i class="far fa-thumbs-up"></i>
                            {{data.boardLikeCnt}}
                            <i class="far fa-comment-dots"></i>
                            {{data.boardCommentCnt}}
                        
                            <i class="far fa-clock"></i>
                            {{createdSimple(data.boardCreatedAt)}}
                        </div>
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
            boardSmallTitle:['자유게시판','질문게시판','리뷰게시판'],
            apiData: {
                '0': {},
                '1': {},
                '2': {},
            },
            basicImg: "https://i4a402.p.ssafy.io/images/basic_profile.png",   
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
        createdSimple(){
            return (date) => {
                var dateArray = date.split('-')
                if (date.length > 10) {
                    return dateArray[0][2]+dateArray[0][3]+'년'+' '+dateArray[1]+'월'+' '+dateArray[2][0] + dateArray[2][1]+'일'
                } else {
                    return date
                }
            }
        }
    },
    created(){
        for (let i=0;i<3;i++){            
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
    max-height: 90%;    
    margin: auto;
    background-color: white;
    font-size: 15px;
    
}
.mainBoardHead {
    display:flex;
    justify-content: space-between;
    
}
.mainBoardHeadTitle {
    margin-top: 20px;
    
}
.mainBoardHead>div:nth-child(2){
    font-size: 24px;
    margin-left: 15%;
    margin-bottom:-2%;
    width:100%;
}
.mainBoardHead>div:nth-child(2)>span{
    font-size: 12px;
    
}

.mainBoardHead + hr {
    width: 100%;
    border-top: 1px solid black;
    margin-bottom: 5px;
}

.leftPin, .rightPin {
    width: 10px;
    height: 10px;
    background-color: black;
    border-radius: 10px;
}

.mainBoardBody {
    display: flex;
    justify-content:left;
    margin: 10px 10px;
    padding: 0 5px 5px 5px;
    border-bottom: 1px solid black;
    
}

.mainBoardBody > div:nth-child(1) {
    display:flex;
    align-items: center;
}
.mainBoardBody > div:nth-child(1)>img {
    width: 55px;
    height:55px;
    border-radius: 30%;
    margin-right: 10px;
    border:0.5px solid rgba(0, 0, 0, 0.123);
}

.mainBoardBody > div:nth-child(2) {
    display:flex;
    flex-direction: column;
    justify-content: space-between;
    width: 100%;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.mainBoardBody > div:nth-child(2)> div:nth-child(1) {
    text-align: left;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}

.mainBoardBody > div:nth-child(2)> div:nth-child(2){
    text-align: right;
    font-size: 10px;
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
        border-top: 10px solid  #d69960;
        border-bottom: 10px solid #d69960;
        border-left: 10px solid #A87A4F;
        border-right: 10px solid  #A87A4F;
    }

    .mainboardBack > div {
        position:relative;
        border-radius: 10px;
        width: 95%;        
        max-height: 100%;    
        margin: auto;
        margin-top: 5%;
        
        
    }
   
    
}

</style>