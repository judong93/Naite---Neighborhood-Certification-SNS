<template>
    <div id='boardlistdetail'>        
        <div class='detailInfo'>
            <div>
                <div class="detailHead">
                    <div class="detailUser">
                        <img :src="apiData.unknownFlag? basicImg:apiData.userPic">
                    </div>
                    <div>
                        <h2 class="detailTitle">{{apiData.boardTitle}}</h2>
                        <div class='detailUserNick' v-if='apiData.unknownFlag'>익명님 {{categoryName[apiData.bigCategoryNo]}}에 남긴 글</div>
                        <div class='detailUserNick' v-else>{{apiData.userNick}}님이 {{categoryName[apiData.bigCategoryNo]}}에 남긴 글 </div>
                    </div>
                </div>
                <div>
                    <div>
                        <div @click='sendMessage()' v-if='thisBoardUserNo !== apiData.userNo&&!apiData.unknownFlag'>
                            <i class="far fa-comments" ></i>
                            <span>메세지</span>
                        </div>
                        <div @click='reportBoard()'>
                            <i class="fas fa-ban" v-if='thisBoardUserNo !== apiData.userNo'></i>
                            <span v-if='thisBoardUserNo !== apiData.userNo'>신고</span>
                        </div>
                        <div @click='otherProfile(apiData.userNo)'  v-if='thisBoardUserNo !== apiData.userNo&&!apiData.unknownFlag'>
                            <i class="fas fa-user-alt" ></i>
                            <span >프로필</span>
                        </div>
                        
                        <div @click='updateBoard()' v-if='thisBoardUserNo === apiData.userNo'>
                            <i class="far fa-edit"  ></i>
                            <span>수정</span>
                        </div>
                        <div @click='deleteBoard()' v-if='thisBoardUserNo === apiData.userNo'>
                            <i class="far fa-trash-alt"></i>
                            <span >삭제</span>
                        </div>
                    </div>                
                    <div>
                        <i class="far fa-clock"></i>
                        <span>{{createdSimple(apiData.boardCreatedAt)}}</span>
                        <i class="far fa-thumbs-up" @click='likeBoard' v-if='!liked'></i>
                        <i class="fas fa-thumbs-up" @click='likeBoard' v-else></i>
                        <span>{{apiData.boardLikeCnt}}</span>
                        <i class="far fa-comment-dots"></i>
                        <span>{{apiData.boardCommentCnt}}</span>               
                    </div>
                </div>
            </div>
            <hr style='background-color:white;margin:10px;'>
            
            <div class="detailBody" v-if='!update' v-html='apiData.boardContent'>
                <!-- 보드의 내용물보이는 부분 -->               
            </div>
            <div class="detailBody" v-else>
                <textarea name="" id="" cols="120" rows="18" v-model='updateContent'></textarea>
            </div>
            
            <div class="detailFooter">
                <button v-if='update' @click = 'updateBoard'>수정취소</button>
                <button v-if='update' @click = 'completeUpdate'>수정하기</button>
            </div>
        </div>  
        <br>
        <BoardDetailImg :boardImg = 'apiData.files' v-if='apiData.files' />
       
    </div>
</template>

<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'
import BoardDetailImg from '@/components/Board/BoardDetailImg'

const SERVER_URL = process.env.VUE_APP_SERVER_URL



export default {
    name:'BoardListDetail',
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
            basicImg: "https://i4a402.p.ssafy.io/images/basic_profile.png",    
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
                    this.$emit('sendMessageDirect',res.data,userNick,this.apiData.userPic,this.apiData.userNo)                    
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
                        this.apiData.boardLikeCnt += 1
                        this.liked = true
                    })
                    .catch(err => {
                        console.log(err)
                    })
            } else {
                axios.post(`${SERVER_URL}/board/dislike`, params,this.setToken())
                    .then(()=>{
                        this.apiData.boardLikeCnt -= 1
                        this.liked = false
                    })
                    .catch(err=>{
                        console.log(err)
                    })
            }

        },
        deleteBoard:function(){
            axios.put(`${SERVER_URL}/board/delete/${this.apiData.boardNo}`,{},this.setToken())
                .then(() => {
                    this.$router.push({name:'Board',params:{bigCategoryNo:this.apiData.bigCategoryNo}})

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
                .then(() => {
                    this.apiData.boardContent = this.updateContent  
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
    computed:{
        createdSimple(){
            return (date) => {
                if (!date){
                    return;
                }
                var dateArray = date.split('-')                
                if (date.length > 10) {
                    return dateArray[0][2]+dateArray[0][3]+'년'+' '+dateArray[1]+'월'+' '+dateArray[2][0] + dateArray[2][1]+'일'
                } else {
                    return date
                }
            }
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
            this.apiData.boardContent.replace(/(?:\r\n|\r|\n)/g, '<br/>')            


        },
        updateCommentCntCheck:function(){            
            this.apiData.boardCommentCnt += 1
        }
        
    },
}
</script>

<style>

#boardlistdetail{
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

.detailInfo {
    width: 100%;
    height: 100%;
    background-color: teal;
    font-family: font1;
    text-align: left;
    padding-bottom: 5%;
}
.detailInfo > div:nth-child(1){
    display: flex;
    justify-content: space-between;

}
.detailHead {
    width: 100%;
    height: 20%;
    display:flex;
    padding: 10px;
}

.detailHead+div{
    display: flex;
    flex-direction: column;
    justify-content: space-between;    
    padding: 10px;
    
}
.detailHead+div>div:nth-child(1){
    display:flex;
    flex-direction: row-reverse;    
}
.detailHead+div>div:nth-child(1)>div {
    margin-left: 15px;
    white-space: nowrap;
    font-size: 14px;
}
.detailHead+div>div:nth-child(1)>div{
    cursor: pointer;
}
.detailHead+div>div:nth-child(2){
    white-space: nowrap;
    text-align: right;
    font-size: 14px;
}
.detailHead+div>div:nth-child(2)>i{
    margin-left: 15px;
}
.detailHead+div>div:nth-child(2)>span{
    margin-left: 5px;
}


.detailUser > img {
    height: 80px;
    width:80px;
    border: 1px solid rgb(172, 172, 172);
    border-radius: 20%;
    background-color: white;
}

.detailUser + div {
    margin-left: 10px;
    display:flex;
    flex-direction: column;
    justify-content: space-between;
}

.detailBody {
    padding:0 10px;    
    overflow: auto;
    overflow-x: hidden;
    white-space: pre-wrap;
    transition:0.3s; 
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
    right:2%;
    cursor: pointer;
    display: flex;
    
}
.detailBar > div > span {
    margin-right: 10px;
    margin-left: 5px;
}


.detailMes{
    cursor:pointer;
    font-size: 14px;
}

.reviewdetailUserNick > span {
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;
}

@media screen and (max-width:501px) {
    #boardlistdetail{
        width: 100%;
        /* max-height: 40%; */
        background-color: #A87A4F;
        top: 8%;
        left: 0%;  
        padding:5px;        
    }

    .detailInfo {
        width: 100%;
        height: 100%;
        background-color: teal;
        font-family: font1;
        text-align: left;
        padding-bottom: 5%;
    }
    .detailInfo > div:nth-child(1){
        display: flex;
        justify-content: space-between;

    }
    .detailHead {
        width: 100%;
        height: 20%;
        display:flex;
        padding: 10px 0 10px 10px;
    }
    .detailHead>div:nth-child(2)>div {
        font-size: 11px;
    }

    .detailHead+div{
        display: flex;
        flex-direction: column;
        justify-content: space-between;    
        padding: 10px 10px 10px 0;
        
    }
    .detailHead > div > h2 {
        max-width: 280px;
        font-size: 18px;
        white-space:normal;
    }
    .detailHead+div>div:nth-child(1){
        display:flex;
        flex-direction: row-reverse;
    }
    .detailHead+div>div:nth-child(1)>div {
        margin-left: 15px;
        white-space: nowrap;
        font-size: 10px;
    }
    .detailHead+div>div:nth-child(2){
        white-space: nowrap;
        text-align: right;
        font-size: 10px;
        /* white-space: normal; */
    }
    .detailHead+div>div:nth-child(2)>i{
        margin-left: 5px;
    }
    .detailHead+div>div:nth-child(2)>span{
        margin-left: 5px;
    }
    
    .detailUser > img {
        height: 40px;
        width:40px;
    }

    .detailUser + div {
        font-size: 12px;
        white-space: nowrap;
        
        
        
    }






    .detailBody {
        padding:0 10px;    
        overflow: auto;
        overflow-x: hidden;
        white-space: pre-wrap;
        transition:0.3s; 
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
        right:2%;
        cursor: pointer;
        display: flex;
        
    }
    .detailBar > div > span {
        margin-right: 10px;
        margin-left: 5px;
    }


    .detailMes{
        cursor:pointer;
        font-size: 14px;
    }


}






</style>