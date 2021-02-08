<template>
    <div id='boardlistdetail'>        
        <div class='detailInfo'>
            <div class="detailHead">
                <div class="detailUser">
                    <img src="../../assets/cha2.png">
                </div>
                <div>
                    <div class="detailTitle">{{apiData.boardTitle}}</div>
                    <div class="detailBar">
                        <i class="far fa-comments" @click='sendMessage'  v-if='thisBoardUserNo !== apiData.userNo'></i>
                        <span @click='sendMessage'  v-if='thisBoardUserNo !== apiData.userNo'>메세지</span>
                        <i class="fas fa-ban" v-if='thisBoardUserNo !== apiData.userNo'></i>
                        <span v-if='thisBoardUserNo !== apiData.userNo'>신고</span>
                        <i class="far fa-edit"  v-if='thisBoardUserNo === apiData.userNo'></i>
                        <span v-if='thisBoardUserNo === apiData.userNo' @click='updateBoard'>수정</span>
                        <i class="far fa-trash-alt" v-if='thisBoardUserNo === apiData.userNo'></i>
                        <span v-if='thisBoardUserNo === apiData.userNo' @click='deleteBoard'>삭제</span>
                        

                    </div>
                    <div class='detailHeadInfo'>
                        <div class='detailUserNick' v-if='apiData.unknownFlag'>익명님 {{categoryName[apiData.bigCategoryNo]}}에 남긴 글</div>
                        <div class='detailUserNick' v-else>{{apiData.userNick}}님이 {{categoryName[apiData.bigCategoryNo]}}에 남긴 글 </div>
                        <div class='detailMes'>
                            <i class="far fa-thumbs-up" @click='likeBoard' v-if='!liked'></i>
                            <i class="fas fa-thumbs-up" @click='likeBoard' v-else></i>
                            <span>{{apiData.boardLikeCnt}}</span>
                            <i class="far fa-comment-dots"></i>
                            <span>{{apiData.boardReportCnt}}</span>
                            <i class="far fa-clock"></i>
                            <span>{{apiData.boardCreatedAt}}</span>
                        </div>


                    </div>
                </div>
            </div>
            <hr style='background-color:white;margin:10px;'>
            <div class="detailBody" v-if='!update'>
                {{apiData.boardContent}}
            </div>
            <div class="detailBody" v-else>
                <textarea name="" id="" cols="87" rows="18" v-model='updateContent'></textarea>
                
            </div>
            <div class="detailFooter">
                <button v-if='update' @click = 'updateBoard'>수정취소</button>
                <button v-if='update' @click = 'completeUpdate'>수정하기</button>
            </div>
        </div>
        <div class='detailImg' v-if='apiData.boardPic'>
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
    name:'BoardListDetail',
    props:{
        apiData:Object,
        boardNo:[String,Number],
    },
    data:function(){
        return {
            categoryName:['','번화가','동사무소','수군수군','소리소문','장터'],
            liked:false,
            thisBoardUserNo:0,
            update:false,
            updateContent: ''
        }
    },
    methods:{
        sendMessage:function(){
            alert('메세지 기능은 추후 업데이트 됩니다. 빠른 시일내에 구현하겠습니다!')
        },
        likeBoard:function(){
            const params = {
                'boardNo':this.boardNo
            }
            if (!this.liked){
                axios.post(`${SERVER_URL}/board/like`,params,this.setToken())
                    .then(res=>{
                        console.log(res)
                        this.apiData.boardLikeCnt += 1
                        this.liked = true
                    })
                    .catch(err => {
                        console.log(err)
                    })
            } else {
                axios.post(`${SERVER_URL}/board/dislike`, params,this.setToken())
                    .then(res=>{
                        console.log(res)
                        this.apiData.boardLikeCnt -= 1
                        this.liked = false
                    })
                    .catch(err=>{
                        console.log(err)
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
        },
        deleteBoard:function(){
            axios.put(`${SERVER_URL}/board/delete/${this.apiData.boardNo}`,{},this.setToken())
                .then(res => {
                    console.log(res)
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
            this.updateContent = this.apiData.boardContent
        },
        completeUpdate:function(){
            const params = {
                'boardContent':this.updateContent,
                'boardPic':this.apiData.boardPic,
                'boardTitle': this.apiData.boardTitle,
                'openFlage':0,
                'unknownFlag':0,
            }
            axios.put(`${SERVER_URL}/board/update/${this.apiData.boardNo}`,params,this.setToken())
                .then(res => {
                    console.log(res)
                    this.apiData.boardContent = this.updateContent  
                    this.update = false
                })
                .catch(err=>{
                    console.log(err)
                })
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
            const img = this.apiData.boardPic
            const headBottom = document.querySelector('.detailHeadInfo')
            const detailBar = document.querySelector('.detailBar')
            if (!img) {
                headBottom.style.width = '1038px'
                detailBar.style.right ='1%'
            } else {
                headBottom.style.width = '740px'
                detailBar.style.right ='28%'
            }
        },
        
    },
}
</script>

<style>

#boardlistdetail{
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

.detailInfo {
    width: 100%;
    height: 100%;
    background-color: teal;
    font-family: font1;
    text-align: left;
}


.detailImg {
    width:400px;
    /* height: 100%; */
    background-color: white;
}

.detailHead {
    width: 100%;
    height: 20%;
    display:flex;
}
.detailTitle {
    width: 80%;
    height:48px;
    text-align: left;
    font-size: 30px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin-left: 12px;
}
.detailUserNick {
    text-align: left;
}


.detailUser > img {
    height: 80px;
    border: 1px solid rgb(172, 172, 172);
    border-radius: 20%;
    background-color: white;
}

.detailHeadInfo{
    width: 740px;    
    margin-left: 12px;
    display: flex;
    justify-content: space-between;
    color:white;
    font-size: 12px;
    margin-top: 10px;
}




.detailHeadInfo i {
    margin: 0 5px;
    margin-right: 10px;
}
.detailHeadInfo span{
    margin-right: 10px;
}

.detailBody {
    padding:0 10px;
    max-height: 190px;
    overflow: auto;
    overflow-x: hidden;
    white-space: normal;
    transition:0.3s
}

.detailBody::-webkit-scrollbar {
    display:none
}

.detailBody:hover::-webkit-scrollbar{
    display:contents;
}

.detailBar {
    position: absolute;
    top:4%;    
    right:28%;
    cursor: pointer;
    
}
.detailBar > span {
    margin-right: 10px;
    margin-left: 5px;
}


.detailMes{
    cursor:pointer;
    font-size: 14px;
}

</style>