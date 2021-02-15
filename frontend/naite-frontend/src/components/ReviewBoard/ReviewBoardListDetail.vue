<template>
    <div id='reviewboardlistdetail'>        
        <div class='reviewdetailInfo'>
            <div class="reviewdetailHead">
                <div class="reviewdetailUser">
                    <img src="../../assets/cha2.png">
                </div>
                <div>
                    <div class="reviewdetailTitle">{{apiData['board'].boardTitle}}</div>
                    <div class="reviewdetailBar">
                        <i class="far fa-comments" @click='sendMessage'  v-if='thisBoardUserNo !== apiData.board.userNo'></i>
                        <span @click='sendMessage'  v-if='thisBoardUserNo !== apiData.board.userNo'>메세지</span>
                        <i class="fas fa-ban" v-if='thisBoardUserNo !== apiData.board.userNo'></i>
                        <span v-if='thisBoardUserNo !== apiData.board.userNo'>신고</span>
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
                    <div class='reviewdetailHeadInfo'>                        
                        <div class='reviewdetailUserNick'>{{apiData.userNick}}님의 {{smallCategoryNmae[apiData.smallCategoryNo]}}시설에 대한 글 
                            <span v-if='apiData.smallCategoryNo===1'>&#xf2e7;</span>
                            <span v-if='apiData.smallCategoryNo===2'>&#xf0f9;</span>
                            <span v-if='apiData.smallCategoryNo===3'>&#xf70c;</span>
                            <span v-if='apiData.smallCategoryNo===4'>&#xf0c4;</span>
                            <span v-if='apiData.smallCategoryNo===5'>&#xf563;</span>
                        </div>
                        <div class='reviewdetailMes'>
                            <i class="far fa-thumbs-up" @click='likeBoard' v-if='!liked'></i>
                            <i class="fas fa-thumbs-up" @click='likeBoard' v-else></i>
                            <span>{{apiData.board.boardLikeCnt}}</span>
                            <i class="far fa-comment-dots"></i>
                            <span>{{apiData.boardCommentCnt}}</span>
                            <i class="far fa-star"></i>
                            <span>{{apiData.reviewStar}}점</span>    
                            <i class="far fa-clock"></i>
                            <span>{{apiData.board.boardCreatedAt}}</span>
                        </div>


                    </div>
                </div>
            </div>
            <hr style='background-color:white;margin:10px;'>            
            <div class="reviewdetailBody" v-if='!update' v-html='apiData.board.boardContent'>
                <!-- 보드의 내용물보이는 부분 -->
            </div>
            <div class="detailBody" v-else>
                <textarea name="" id="" cols="87" rows="18" v-model='updateContent'></textarea>
                
            </div>
            <div class="detailFooter">
                <button v-if='update' @click = 'updateBoard'>수정취소</button>
                <button v-if='update' @click = 'completeUpdate'>수정하기</button>
            </div>
        </div>
        <div class='reviewdetailImg' v-if='apiData.board.boardPic'>
        <!-- <div class='detailImg'> -->
            <img src="https://i4a402.p.ssafy.io/img/signpast.fbb26c75.jpg" alt="" width='100%'>
        </div>

    </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'
const today = new Date()
console.log(today)

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'



export default {
    name:'ReviewBoardListDetail',
    props:{
        apiData:Object,
        boardNo:[String,Number],
        updateCommentCntCheck:Number,
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
                    this.$router.push({name:'Board',params:{bigCategoryNo:this.apiData.board.bigCategoryNo}})

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

            for (let i=0; i<this.apiData.usersWithLike.length;i++) {
                if (this.apiData.usersWithLike[i] === decode.user.userNick) {
                    this.liked = true                
                }
            }
         

            const img = this.apiData.board.boardPic
            const headBottom = document.querySelector('.reviewdetailHeadInfo')
            const detailBar = document.querySelector('.reviewdetailBar')
                        
            this.apiData.board.boardContent.replace(/(?:\r\n|\r|\n)/g, '<br/>')
            this.apiData.board.boardContent.split('\n').join('<br />')
            
            if (!img) {
                headBottom.style.width = '1038px'
                detailBar.style.right ='1%'
            } else {
                headBottom.style.width = '740px'
                detailBar.style.right ='28%'
            }
        },
        updateCommentCntCheck:function(){            
            this.apiData.boardCommentCnt += 1
        }
        
    },
}
</script>

<style>

#reviewboardlistdetail{
    position:absolute;
    width: 60%;
    height: 40%;
    background-color: #A87A4F;
    top: 13%;
    left: 20%;  
    display:flex;  
    padding:10px;
    color:white;
}

.reviewdetailInfo {
    width: 100%;
    height: 100%;
    background-color: teal;
    font-family: font1;
    text-align: left;
}


.reviewdetailImg {
    width:400px;
    /* height: 100%; */
    background-color: white;
}

.reviewdetailHead {
    width: 100%;
    height: 20%;
    display:flex;
}
.reviewdetailTitle {
    width: 80%;
    height:48px;
    text-align: left;
    font-size: 30px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin-left: 12px;
}
.reviewdetailUserNick {
    text-align: left;
}

.reviewdetailUserNick span {
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;    
}


.reviewdetailUser > img {
    height: 80px;
    border: 1px solid rgb(172, 172, 172);
    border-radius: 20%;
    background-color: white;
}

.reviewdetailHeadInfo{
    width: 740px;    
    margin-left: 12px;
    display: flex;
    justify-content: space-between;
    color:white;
    font-size: 12px;
    margin-top: 10px;
}




.reviewdetailHeadInfo i {
    margin: 0 5px;
    margin-right: 10px;
}
.reviewdetailHeadInfo span{
    margin-right: 10px;
}

.reviewdetailBody {
    padding:0 10px;
    max-height: 190px;
    overflow: auto;
    overflow-x: hidden;
    white-space: pre-wrap;
    transition:0.3s
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
    right:28%;
    cursor: pointer;
    
}
.reviewdetailBar > span {
    margin-right: 10px;
    margin-left: 5px;
}


.reviewdetailMes{
    cursor:pointer;
    font-size: 14px;
}

</style>