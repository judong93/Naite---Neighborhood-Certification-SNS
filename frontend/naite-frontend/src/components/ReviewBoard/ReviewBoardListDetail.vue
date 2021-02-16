<template>
    <div id='reviewboardlistdetail'>        
        <div class='reviewdetailInfo'>
            <div>
                <div class="reviewdetailHead">
                    <div class="reviewdetailUser">
                        <img :src="apiData.userPic">
                    </div>
                    <div>
                        <h2 class="reviewdetailTitle">{{apiData['board'].boardTitle}}</h2>
                        <div class='reviewdetailUserNick'>{{apiData.userNick}}님의 {{smallCategoryNmae[apiData.smallCategoryNo]}}리뷰 
                            <span v-if='apiData.smallCategoryNo===1'>&#xf2e7;</span>
                            <span v-if='apiData.smallCategoryNo===2'>&#xf0f9;</span>
                            <span v-if='apiData.smallCategoryNo===3'>&#xf70c;</span>
                            <span v-if='apiData.smallCategoryNo===4'>&#xf0c4;</span>
                            <span v-if='apiData.smallCategoryNo===5'>&#xf563;</span>
                        </div>
                    </div>
                </div>
                <div>
                    <div>
                        <div @click='sendMessage' v-if='thisBoardUserNo !== apiData.board.userNo'>
                            <i class="far fa-comments" ></i>
                            <span>메세지</span>
                        </div>
                        <div @click='reportBoard()' v-if='thisBoardUserNo !== apiData.board.userNo'>
                            <i class="fas fa-ban"></i>
                            <span>신고</span>
                        </div>
                        <div @click='otherProfile(apiData.userNo)'>
                            <i class="fas fa-user-alt"  v-if='thisBoardUserNo !== apiData.userNo'></i>
                            <span   v-if='thisBoardUserNo !== apiData.userNo'>프로필</span>
                        </div>
                        <div v-if='thisBoardUserNo === apiData.board.userNo' style='display:flex'>
                            <div  @click='updateBoard' style='margin-right:10px'>
                                <i class="far fa-edit"  ></i>
                                <span>수정</span>
                            </div>
                            <div @click='deleteBoard'>
                                <i class="far fa-trash-alt"></i>
                                <span >삭제</span>
                            </div>
                            
                        </div>
                    </div>
                    
                    <div>
                        <i class="far fa-star"></i>
                        <span>{{apiData.reviewStar}}점</span>    
                        <i class="far fa-clock"></i>
                        <span>{{apiData.boardCreatedAt}}</span>
                        <i class="far fa-thumbs-up" @click='likeBoard' v-if='!liked'></i>
                        <i class="fas fa-thumbs-up" @click='likeBoard' v-else></i>
                        <span>{{apiData.board.boardLikeCnt}}</span>
                        <i class="far fa-comment-dots"></i>
                        <span>{{apiData.boardCommentCnt}}</span>
                    </div>



                </div>


            </div>

            <hr style='background-color:white;margin:10px;'>                       
            <div class="reviewdetailBody" v-if='!update' v-html='apiData.board.boardContent'>
                <!-- 보드의 내용물보이는 부분 -->
            </div>            
            <div class="detailBody" v-else>
                <textarea name="" id="" cols="87" rows="18" v-model='updateContent'>
                </textarea>               
            </div>
            <div class="detailFooter">
                <button v-if='update' @click = 'updateBoard'>수정취소</button>
                <button v-if='update' @click = 'completeUpdate'>수정하기</button>
            </div>
        </div>
        <div class='reviewdetailImg' v-if='apiData.board.boardPic'>
            <!-- <img src="https://i4a402.p.ssafy.io/images/board/254_202102150613499_2.jpg" alt="" width='100%'> -->
        
        </div>
        <BoardDetailImg :boardImg = 'apiData.files' v-if='apiData.files' />

    </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'
import BoardDetailImg from '@/components/Board/BoardDetailImg'


const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'



export default {
    name:'ReviewBoardListDetail',
    props:{
        apiData:Object,
        boardNo:[String,Number],
        updateCommentCntCheck:Number,
    },
    components:{
        BoardDetailImg,
    },
    data:function(){
        return {
            categoryName:['','번화가','동사무소','수군수군','소리소문','장터'],
            liked:false,
            thisBoardUserNo:0,
            update:false,
            updateContent: '',
            smallCategoryNmae:['','식당','의료','체육시설','미용','기타'],
        }
    },
    methods:{
        otherProfile:function(userNo){
            this.$router.push({name:'Profile',params:{userNo:userNo}})

        },
         reportBoard:function(){
            const params = {
                "boardNo": this.boardNo,
                "commentNo": 0,
                "reportTargetNo": this.apiData.userNo,
                "reportType": 0,
            }
            axios.post(`${SERVER_URL}/report/insert`,params,this.setToken())
                .then(res=>{
                    if (res.data.response ==='error'){
                        alert(res.data.message)
                    } else {
                        alert(`${this.apiData.boardTitle} 글에 대한 신고가 접수되었습니다`)
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        sendMessage:function(){
            const userNick = this.apiData.userNick
            
            axios.post(`${SERVER_URL}/chat/room?otherNick=${userNick}`,{},this.setToken())
                .then(res=>{
                    this.$emit('sendMessageDirect',res.data,userNick)
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        likeBoard:function(){
            const params = {
                'boardNo':this.boardNo
            }
            if (!this.liked){
                axios.post(`${SERVER_URL}/board/like`,params,this.setToken())
                    .then(()=>{                        
                        this.apiData.board.boardLikeCnt += 1
                        this.liked = true
                    })
                    .catch(err => {
                        console.log(err)
                    })
            } else {
                axios.post(`${SERVER_URL}/board/dislike`, params,this.setToken())
                    .then(res=>{
                        console.log(res)
                        this.apiData.board.boardLikeCnt -= 1
                        this.liked = false
                    })
                    .catch(err=>{
                        console.log(err)
                    })
            }

        },
        deleteBoard:function(){
            axios.put(`${SERVER_URL}/board/delete/${this.apiData.board.boardNo}`,{},this.setToken())
                .then(() => {
                    this.$router.push({name:'ReviewBoard'})

                })
                .catch(err => {
                    console.log(err)
                })
        },
        updateBoard:function(){
            if (this.update) {
                this.update = false
            } else {
                this.update = true
            }
            this.updateContent = this.apiData.board.boardContent
        },
        completeUpdate:function(){
            const params = {
                'boardContent':this.updateContent,
                'boardPic':this.apiData.boardPic,
                'boardTitle': this.apiData.board.boardTitle,
                'openFlage':0,
                'unknownFlag':0,
            }
            axios.put(`${SERVER_URL}/board/update/${this.apiData.board.boardNo}`,params,this.setToken())
                .then(res => {
                    console.log(res)
                    this.apiData.board.boardContent = this.updateContent  
                    this.update = false
                })
                .catch(err=>{
                    console.log(err)
                })
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


    },
    watch:{
        apiData:function(){
            const decode = jwt_decode(localStorage.getItem('jwt'))
            this.thisBoardUserNo = decode.user.userNo   
            console.log(this.apiData)
            for (let i=0; i<this.apiData.usersWithLike.length;i++) {
                if (this.apiData.usersWithLike[i] === decode.user.userNick) {
                    this.liked = true                
                }
            }       
            this.apiData.board.boardContent.replace(/(?:\r\n|\r|\n)/g, '<br/>')          
        },
        updateCommentCntCheck:function(){            
            this.apiData.boardCommentCnt += 1
        }
        
    },
}
</script>

<style>

#reviewboardlistdetail{
    position:relative;
    width: 60%;
    /* max-height: 40%; */
    background-color: #A87A4F;
    top: 13%;
    left: 20%;  
    display:flex;  
    padding:10px;
    color:white;
    flex-wrap: wrap;
    
}

.reviewdetailInfo {
    width: 100%;
    height: 100%;
    background-color: teal;
    font-family: font1;
    text-align: left;
    padding-bottom: 5%;
}
.reviewdetailInfo > div:nth-child(1){
    display: flex;
    justify-content: space-between;

}
.reviewdetailHead {
    width: 100%;
    height: 20%;
    display:flex;
    padding: 10px;
}

.reviewdetailHead+div{
    display: flex;
    flex-direction: column;
    justify-content: space-between;    
    padding: 10px;
    
}
.reviewdetailHead+div>div:nth-child(1){
    display:flex;
    flex-direction: row-reverse;    
}
.reviewdetailHead+div>div:nth-child(1)>div {
    margin-left: 15px;
    white-space: nowrap;
    font-size: 14px;
}
.reviewdetailHead+div>div:nth-child(1)>div{
    cursor: pointer;
}
.reviewdetailHead+div>div:nth-child(2){
    white-space: nowrap;
    text-align: right;
    font-size: 14px;
}
.reviewdetailHead+div>div:nth-child(2)>i{
    margin-left: 15px;
}
.reviewdetailHead+div>div:nth-child(2)>span{
    margin-left: 5px;
}


.reviewdetailUser > img {
    height: 80px;
    width:80px;
    border: 1px solid rgb(172, 172, 172);
    border-radius: 20%;
    background-color: white;
}

.reviewdetailUser + div {
    margin-left: 10px;
    display:flex;
    flex-direction: column;
    justify-content: space-between;
}

.reviewdetailBody {
    padding:0 10px;    
    overflow: auto;
    overflow-x: hidden;
    white-space: pre-wrap;
    transition:0.3s; 
}

.reviewdetailBody::-webkit-scrollbar {
    display:none
}

.reviewdetailBody:hover::-webkit-scrollbar{
    display:contents;
}

.reviewdetailBar {
    position: absolute;
    top:4%;    
    right:2%;
    cursor: pointer;
    display: flex;
    
}
.reviewdetailBar > div > span {
    margin-right: 10px;
    margin-left: 5px;
}


.reviewdetailMes{
    cursor:pointer;
    font-size: 14px;
}

@media screen and (max-width:501px) {
    #reviewboardlistdetail{
        width: 100%;
        /* max-height: 40%; */
        background-color: #A87A4F;
        top: 8%;
        left: 0%;  
        padding:5px;        
    }

    .reviewdetailInfo {
        width: 100%;
        height: 100%;
        background-color: teal;
        font-family: font1;
        text-align: left;
        padding-bottom: 5%;
    }
    .reviewdetailInfo > div:nth-child(1){
        display: flex;
        justify-content: space-between;

    }
    .reviewdetailHead {
        width: 100%;
        height: 20%;
        display:flex;
        padding: 10px 0 10px 10px;
    }
    .reviewdetailHead>div:nth-child(2)>div {
        font-size: 11px;
    }

    .reviewdetailHead+div{
        display: flex;
        flex-direction: column;
        justify-content: space-between;    
        padding: 10px 10px 10px 0;
        
    }
    .reviewdetailHead > div > h2 {
        max-width: 250px;
        font-size: 18px;
        white-space:normal;
    }
    .reviewdetailHead+div>div:nth-child(1){
        display:flex;
        flex-direction: row-reverse;
    }
    .reviewdetailHead+div>div:nth-child(1)>div {
        margin-left: 15px;
        white-space: nowrap;
        font-size: 10px;
    }
    .reviewdetailHead+div>div:nth-child(2){
        white-space: nowrap;
        text-align: right;
        font-size: 10px;
        /* white-space: normal; */
    }
    .reviewdetailHead+div>div:nth-child(2)>i{
        margin-left: 5px;
    }
    .reviewdetailHead+div>div:nth-child(2)>span{
        margin-left: 5px;
    }
    
    .reviewdetailUser > img {
        height: 40px;
        width:40px;
    }

    .reviewdetailUser + div {
        font-size: 12px;
        white-space: nowrap;
    }






    .reviewdetailBody {
        padding:0 10px;    
        overflow: auto;
        overflow-x: hidden;
        white-space: pre-wrap;
        transition:0.3s; 
    }

    .reviewdetailBody::-webkit-scrollbar {
        display:none
    }

    .reviewdetailBody:hover::-webkit-scrollbar{
        display:contents;
    }

    .reviewdetailBar {
        position: absolute;
        top:4%;    
        right:2%;
        cursor: pointer;
        display: flex;
        
    }
    .reviewdetailBar > div > span {
        margin-right: 10px;
        margin-left: 5px;
    }


    .reviewdetailMes{
        cursor:pointer;
        font-size: 14px;
    }


}

    
</style>