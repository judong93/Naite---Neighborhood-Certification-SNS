<template>
    <div id="boardComment">

        <button class='goCreateComment' @click='toCommentInput' v-if='scrollHeight'>댓글등록</button>


        <div class='commentList' v-for='(comment,idx) in apiData' :key='idx'>
            <!-- 부모댓글 -->   
            <div class="commentParent" v-if='!comment.parentId && !comment.recomment'>

                <header>
                    <div>
                        <div>
                            <img :src="comment.isUnknown? basicImg : comment.userPic" alt="" width='40px' height='40px'>
                        </div>

                        <span v-if='!comment.isDeleted'>{{comment.isUnknown ? '익명':comment.userNick}}</span>
                        <span v-else class='deleted'>삭제된 글</span>
                    </div>
                    <div>                        
                        <div @click='recomment(idx)'>
                            <i class="fas fa-share"></i>
                            <span>대댓글 달기</span>
                        </div>
                        <div v-if='comment.userOwn && !comment.isDeleted' @click='commentUpdate(comment.commentNo,idx)'>
                            <i class="far fa-edit"></i>
                            <span >수정</span>
                        </div>
                        <div v-if='!comment.userOwn && !comment.isDeleted' @click='reportComment(comment.commentNo,comment.userNo)'>
                            <i class="fas fa-ban"></i>
                            <span >댓글신고</span>
                        </div>
                        <div  v-if='comment.userOwn && !comment.isDeleted' @click='deleteComment(idx,comment.commentNo)'>
                            <i class="far fa-trash-alt"></i>
                            <span>삭제</span>
                        </div>
                        <div v-if='!comment.userOwn && !comment.isDeleted&&!comment.isUnknown' @click='sendMessage(comment.userNick,comment.userPic,comment.userNo)'>
                            <i class="far fa-comment-dots"></i>
                            <span>메세지 보내기</span>
                        </div>
                        <div @click='otherProfile(comment.userNo)' v-if='!comment.userOwn && !comment.isDeleted&&!comment.isUnknown'>
                            <i class="fas fa-user-alt"></i>
                            <span>프로필</span>
                        </div>
                    </div>
                </header>
                <body v-if='idx!==updatedIdx'>
                    <span class='commentContent deleted' v-if='comment.isDeleted'>삭제된 댓글입니다</span>
                    <span class='commentContent' v-else>{{comment.content}}</span><br>
                    <span>{{createdSimple(comment.createdAt)}}</span>
                </body>
                <body v-else style='display:flex;justify-content:space-between'>                  
                    <input type="text" v-model='updatedContent' @keypress.enter="commentUpdateComplete" style='width:70%'>
                    <div style='display:flex' class='updateafterbtn'>
                        <button @click='commentUpdateComplete'>댓글수정</button>
                        <button @click='cancleUpdate'>수정취소</button>
                    </div>
                </body>
            </div>
            
            <!-- 자식댓글 -->
            <div class="recomment" v-else-if='comment.parentId && !comment.recomment'>

                <header>
                    <div>                        
                        <i class="fas fa-share"></i>
                        <div>
                            <img :src="comment.isUnknown ? basicImg :comment.userPic" alt="" width='40px' height='40px'>
                        </div>

                        <span v-if='!comment.isDeleted'>{{comment.isUnknown ? '익명':comment.userNick}}</span>
                        <span v-else class='deleted'>삭제된 글</span>
                    </div>
                    <div>
                        <div v-if='comment.userOwn && !comment.isDeleted'  @click='commentUpdate(comment.commentNo,idx)'>
                            <i class="far fa-edit"></i>
                            <span >수정</span>
                        </div>
                        <div v-if='!comment.userOwn && !comment.isDeleted' @click='reportComment(comment.commentNo,comment.userNo)'>
                            <i class="fas fa-ban"></i>
                            <span >댓글신고</span>
                        </div>
                        <div  v-if='comment.userOwn && !comment.isDeleted' @click='deleteComment(idx,comment.commentNo)'>
                            <i class="far fa-trash-alt"></i>
                            <span>삭제</span>
                        </div>
                        <div v-if='!comment.userOwn && !comment.isDeleted&&!comment.isUnknown' @click='sendMessage(comment.userNick,comment.userPic,comment.userNo)'>
                            <i class="far fa-comment-dots"></i>
                            <span>메세지 보내기</span>
                        </div>
                        <div @click='otherProfile(comment.userNo)' v-if='!comment.userOwn && !comment.isDeleted&&!comment.isUnknown'>
                            <i class="fas fa-user-alt"></i>
                            <span>프로필</span>
                        </div>
                    </div>
                </header>
                <body v-if='idx!==updatedIdx'>
                    <span class='commentContent deleted' v-if='comment.isDeleted'>삭제된 댓글입니다</span>
                    <span class='commentContent' v-else>{{comment.content}}</span><br>
                    <span>{{createdSimple(comment.createdAt)}}</span>
                </body>
                <body v-else style='display:flex;justify-content:space-between'>                  
                    <input type="text" v-model='updatedContent' @keypress.enter="commentUpdateComplete" style='width:70%'>
                    <div style='display:flex' class='updateafterbtn'>
                        <button @click='commentUpdateComplete'>댓글수정</button>
                        <button @click='cancleUpdate'>수정취소</button>
                    </div>
                </body>
                
            </div>


            <div v-else style='display:flex;justify-content:flex-end'>
                <div class='recommentUnknownCheck' v-if='bigCategoryNo===1'>
                    <span>익명</span> 
                    <input type="checkbox" class='recommentUnknownCheckBox' >
                </div>
                <input class='recommentInput' type="text" v-model='recommentParams.content' @keypress.enter=createRecomment(recommentParentId,idx)>
                <button @click='createRecomment(recommentParentId,idx)'>대댓글등록</button>
            </div>



        </div>
        <div class='createComment' style='display:flex;justify-content:flex-end'> 
            <div class='commentUnknownCheck' v-if='bigCategoryNo===1'>
                <span>익명</span> <input type="checkbox" class='unKnownComment' >
            </div>
            <input type="text" class="commentinput"  @keypress.enter='createComment(0)' v-model='params.content'>
            <button @click='createComment(0)'>댓글작성</button>
        </div>    



    </div>
</template>
<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'
const SERVER_URL = process.env.VUE_APP_SERVER_URL


export default {
    name:'BoardComment',
    data:function(){
        return{
            params: {
                'boardId':0,
                'content':'',
                'isUnknown':0,
                'parentId':1,
            },
            recommentParams:{
                'boardId':0,
                'content':'',
                'isUnknown':0,
                'parentId':1,
                'userOwn':1,
                'createdAt':'현재',
                
            },
            apiData:[],
            lastRecommentIdx:-1,    
            recommentContent:'', 
            recommentParentId:-1,
            basicImg: "https://i4a402.p.ssafy.io/images/basic_profile.png",
            scrollHeight:'',
            updatedContent:'',   
            updateCommentNo:'',
            updatedIdx:-1,   
        }
    },
    props:{
        bigCategoryNo:Number,
    },

    methods:{ 
        otherProfile:function(userNo){
            this.$router.push({name:'Profile',params:{userNo:userNo}})

        },
        commentUpdateComplete:function(){
            axios.put(`${SERVER_URL}/comment/${this.updateCommentNo}`,{'commentContent':this.updatedContent},this.setToken())
                .then(() => {
                    this.apiData[this.updatedIdx].content = this.updatedContent
                    this.updateCommentNo = -1
                    this.updatedIdx = -1
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        cancleUpdate:function(){
            this.updatedIdx = -1

        },
        commentUpdate:function(commentNo,idx){
            this.updateCommentNo = commentNo
            this.updatedIdx = idx
            this.updatedContent = this.apiData[idx].content

        },
        sendMessage:function(Nick,pic,num){
            const userNick = Nick

            axios.post(`${SERVER_URL}/chat/room?otherNick=${userNick}`,{},this.setToken())
                .then(res=>{
                    this.$emit('sendMessageDirect',res.data,userNick,pic,num) 
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        reportComment:function(commentNo,userNo){
            const param = {
                'boardNo':0,
                'commentNo': commentNo,
                'reportTargetNo': userNo,
                'reportType':1,
            }
            axios.post(`${SERVER_URL}/report/insert`,param,this.setToken())
                .then(res=>{
                    if (res.data.response ==='error'){
                        alert(res.data.message)
                    } else {
                        alert('신고접수가 완료되었습니다!')
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        updateCommentCnt:function(){
            this.$emit('updateCommentCnt')
        },
        toCommentInput:function(){
            const inputBtn = document.querySelector('.commentinput')
            inputBtn.focus()
        },
        createComment:function(num){
            this.params.boardId = this.$route.params.boardNo
            this.params.parentId = num
            if (this.bigCategoryNo===1){
                const unKnownComment = document.querySelector('.unKnownComment')
                if (unKnownComment.checked) {
                    this.params.isUnknown = 1
                } else {
                    this.params.isUnknown = 0
                }
                
            }
            axios.post(`${SERVER_URL}/comment`,this.params,this.setToken())
                .then(res=>{
                    if(res.data.response==='error') {
                        alert('오류발생/로그아웃 후 재진행')
                        localStorage.removeItem('jwt')
                        this.$router.push({name:'Sign'})
                    } else if (this.params.content) {                                               
                        const decode = jwt_decode(localStorage.getItem('jwt'))
                        const param = {
                            'content':this.params.content,
                            'isUnknown':this.params.isUnknown,
                            'userNick': decode.user.userNick,
                            'parentId':0,
                            'userOwn':1,
                            'createdAt':'현재',
                            'commentNo':res.data.data.commentNo,
                            'userPic':res.data.data.userPic
                        }                        
                        this.updateCommentCnt()
                        this.apiData.push(param)
                        this.params.content=''
                    } else {                        
                        alert('내용물을 작성해주세요')
                    }

                })
                .catch(err=>{
                    console.log(err)
                })
        },
        createRecomment:function(parentId,idx){
            // const comment = this.recommentContent
            this.recommentParams.boardId = this.$route.params.boardNo
            this.recommentParams.parentId = parentId
            if (this.bigCategoryNo===1){
                const unKnownComment = document.querySelector('.recommentUnknownCheckBox')
                if (unKnownComment.checked) {
                    this.recommentParams.isUnknown = 1
                } else {
                    this.recommentParams.isUnknown = 0
                }
                
            }            
            axios.post(`${SERVER_URL}/comment`,this.recommentParams,this.setToken())
                .then(res=>{
                    if(res.data.response==='error') {
                        alert('오류발생/로그아웃 후 재진행')
                        localStorage.removeItem('jwt')
                        this.$router.push({name:'Sign'})
                    } else if (this.recommentParams.content) {
                        const decode = jwt_decode(localStorage.getItem('jwt'))                        
                        const param = {
                            'content':this.recommentParams.content,
                            'isUnknown':this.recommentParams.isUnknown,
                            'userNick': decode.user.userNick,
                            'parentId':this.recommentParams.parentId,
                            'userOwn':1,
                            'createdAt':'현재',
                            'commentNo':res.data.data.commentNo,                            
                            'userPic':res.data.data.userPic
                        }
                        this.updateCommentCnt()
                        this.recommentParams.userNick = decode.user.userNick 
                        this.apiData.splice(idx,1)
                        if (idx===this.apiData.length){
                            this.apiData.push(param)
                        } else {
                            this.apiData.splice(idx,0,param)
                        }
                        this.lastRecommentIdx = -1
                        this.recommentParentId = -1                       
                    } else {                        
                        alert('내용물을 작성해주세요')
                    }

                })
                .catch(err=>{
                    console.log(err)
                })

        },
        deleteComment:function(idx,commentIdx){

            axios.delete(`${SERVER_URL}/comment/${commentIdx}`,this.setToken())
                .then(res => {
                    if (res.data.response === 'error') {
                        alert(res.data.message)
                    } else {                        
                        this.apiData[idx].content = '삭제된 댓글입니다'
                        this.apiData[idx].isDeleted = 1
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        recomment:function(idx){
            setTimeout(() => {
                const recommentInput = document.querySelector('.recommentInput')
                recommentInput.focus()
            }, 500);
            this.recommentParams.content = ''
            const parentId = this.apiData[idx].commentNo            
            const recommentInput = {
                recomment:1,
            }

            if (this.lastRecommentIdx !==-1){
                this.apiData.splice(this.lastRecommentIdx,1)
            }
            this.lastRecommentIdx = -1
            
            
            let addIdx = 0

            for (let i=0; i<this.apiData.length;i++){
                if (this.apiData[i].commentNo === parentId){
                    addIdx = i
                }
                if (this.apiData[i].parentId === parentId){
                    addIdx = i
                }
            }
            this.apiData.splice(addIdx+1,0,recommentInput)


            if (document.querySelector('.recommentInput')){
                document.querySelector('.recommentInput').focus()
            } 

            this.lastRecommentIdx = addIdx+1
            this.recommentParentId = parentId
            
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
    created(){
        const No = this.$route.params.boardNo
        axios.get(`${SERVER_URL}/comment/${No}`,this.setToken())
            .then(res=>{
                if (res.data.response==='error'){
                    alert('로그인을 다시 진행해주세요.')
                } else {
                    this.apiData = res.data.data   
                }
            })
            .catch(err=>{
                console.log(err)
            })
        const height = scroll.height
        if (height>900){
            this.scrollHeight = true
        } else {
            this.scrollHeight = false
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
                    return dateArray[0][2]+dateArray[0][3]+'년'+' '+dateArray[1]+'월'+' '+dateArray[2][0] + dateArray[2][1]+'일' + ' '+ dateArray[2][3] + dateArray[2][4]+ '시' + ' '+ dateArray[2][6]+ dateArray[2][7]+ dateArray[2][8]+'분'
                } else {
                    return date
                }
            }
        }
    },
}
</script>
<style>
#boardComment {
    position:relative;
    top:18%;
    left:20%;
    width:60%;
    padding:1%;
    font-family: font1; 
    border:1px solid #cccccc; 
    background-color: white;   
    border-radius: 10px;
}

.goCreateComment {
    position:absolute;
    top:-2%;
    right:0;
    border:none;
    outline:none;
    border-radius: 5px;
    background-color: rgb(78, 101, 230);
    color:white;

}

.commentList > div {
    padding:10px 0;
    
}

.commentParent > header {
    display:flex;  
    justify-content: space-between;  
}

.commentParent > header > div {
    display:flex;
    align-items: flex-end;
}


.commentParent > header > div:nth-child(1) > div {
    border-radius: 10px;
    background-color: grey;
    
}
.commentParent > header > div:nth-child(1) >span {
    margin-left: 10%;
    font-size: 15px;
    white-space: nowrap;
}
.commentParent > header > div:nth-child(2) {
    font-size: 12px;
    cursor:pointer;
    /* margin-left: 100px; */
}
.commentParent > header > div:nth-child(2)>div{
    margin-right: 10px;
}


.commentParent > body {
    padding:10px;
    /* border: 1px black solid; */
    border-radius: 5px;
    font-family: font1;
    /* background-color: rgba(171, 228, 115, 0.726);     */
    background-color: #a87a4f;
    color:white;
}

.commentParent > body >span:nth-child(3){
    font-size: 10px;
}

/* 여기부턴 대댓글 */



.recomment {
    position:relative;
    padding:10px 0;   
    left:10%;
    width:90%; 
}

.recomment > header {
    display:flex;  
    justify-content: space-between;  
}

.recomment > header > div {
    display:flex;
    align-items: flex-end;
}


.recomment > header > div:nth-child(1) > div {
    border-radius: 10px;
    background-color: grey;
    /* overflow: hidden; */
    /* border:1px solid rgba(202, 198, 198, 0) */
}
.recomment > header > div:nth-child(1) >span {
    margin-left: 10%;
    font-size: 15px;
    white-space: nowrap;
}
.recomment > header > div:nth-child(2) {
    font-size: 12px;
    cursor:pointer;
    /* margin-left: 100px; */
}
.recomment > header > div:nth-child(2)>div{
    margin-right: 10px;
}


.recomment > body {
    padding:10px;
    border-radius: 5px;
    font-family: font1;
    /* background-color: rgb(178, 240, 116);     */
    background-color: #a87a4fc2;
    color:white;
}

.recomment > body >span:nth-child(3){
    font-size: 10px;
}

.recomment > i {

    position:absolute;
    left:-10px;
}




.deleted {
    font-style: italic;
    text-decoration:line-through ;
}

.recommentUnknownCheck{
    margin-right: 10px;
    background-color: rgb(43, 87, 41);
    color:white;
    padding:3px 10px;
    border-radius: 5px;
}

.recommentInput {
    position:relative;
    width:80%;
    left:0%;
    border:none;
    outline:none;
    background-color:rgb(145, 187, 103); 
    border-radius: 5px;
    padding: 5px;
    color:white;
}

.recommentInput+button {
    border:none;
    outline:none;
    cursor:pointer;
    padding:5px;
    color:white;
    background-color: black;
    border-radius: 5px;
}
.commentUnknownCheck{
    margin-right: 10px;
    background-color: rgba(38, 95, 35, 0.76);
    color:white;
    padding:3px 10px;
    border-radius: 5px;
}

.commentinput {
    position:relative;
    width:80%;
    left:0%;
    border:none;
    outline:none;
    background-color:rgba(145, 187, 103, 0.671); 
    border-radius: 5px;
    padding: 5px;
    color:white;
}

.commentinput+button {
    border:none;
    outline:none;
    cursor:pointer;
    padding:5px;
    color:white;
    background-color: rgba(0, 0, 0, 0.712);
    border-radius: 5px;
}
/* 
.createComment {
    position:absolute;
    bottom:-20%;
} */

#boardComment img {
    border-radius: 10px;
    border:0.5px solid rgb(182, 182, 182);
}


@media screen and (max-width:501px) {
    #boardComment {
        top:10%;
        left:0.5%;
        width:98%;
    }
    .commentList > div {
        padding:5px 0;
        
    }

    .commentParent > header > div:nth-child(1) >span {
        font-size: 12px;
     
    }
    .commentParent > header > div:nth-child(2) {
        font-size: 10px;
    }
    .commentParent > header > div:nth-child(2)>div{
        margin-right: 10px;
    }


    .commentParent > body {
        padding:5px;
        font-size: 14px;
    }

    .commentParent > body >span:nth-child(3){
        font-size: 8px;
    }

    /* 여기부턴 대댓글 */



    .recomment {
        padding:2px 0;   
    }

    .recomment > header > div:nth-child(1) >span {
        font-size: 12px;
    }
    .recomment > header > div:nth-child(2) {
        font-size: 10px;
    }
    .recomment > body {
        padding:5px;
        font-size: 12px;
    }

    .recomment > body >span:nth-child(3){
        font-size: 6px;
    }

    .recommentUnknownCheck{
        margin-right: 5px;
        background-color: rgb(43, 87, 41);
        color:white;
        padding:2px 2px;
        font-size: 10px;
    }

    .recommentInput {
        width:60%;
    }

    .recommentInput+button {
        font-size: 10px;
    }
    .commentUnknownCheck{
         margin-right: 5px;
        background-color: rgb(43, 87, 41);
        color:white;
        padding:2px 2px;
        font-size: 10px;
        
    }

    .commentinput {
       width:70%;
       
    }

    .commentinput+button {
        font-size: 10px;
    }
    .createComment {
        margin-bottom: 50px;
    }
    .updateafterbtn {
        font-size: 10px;
    }
    .updateafterbtn > input {
        width: 50%;
    }
}




</style>