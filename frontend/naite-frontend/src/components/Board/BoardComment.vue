<template>
    <div id="boardComment">
        <div>
            <div class="commentlist" v-for='(comment,idx) in apiData' :key='idx'>
                <div class="parentComment" v-if='comment.parentId===0'>   
                    <div class='commentLeft'>
                        <img src="../../assets/cha2.png" alt="" height="40px">
                        <span class='commentNick'>{{comment.userNick}} : </span> 
                    </div>
                    <span class='commentContent deleted' v-if='comment.isDeleted'>삭제된 댓글입니다</span>
                    <span class='commentContent' v-else>{{comment.content}}</span>
                    <span style='font-size:10px;white-space:nowrap;margin-right:10px'>{{comment.createdAt}} 작성된 글</span>
                    <!-- {{timdeDiff(comment.createdAt)}} -->
                    <div class='commentStatus'>
                        <div style='display:flex'>
                            <div @click='recomment(idx)'>
                                <i class="fas fa-share"></i><span>대댓글 달기</span>
                            </div>
                            <span v-if='comment.userOwn && !comment.isDeleted' style='margin-left:10px'>수정</span>
                            <span v-if='comment.userOwn  && !comment.isDeleted' style='margin-left:10px' @click='deleteComment(idx,comment.commentNo)'>삭제</span>
                        </div>
                        

                    </div>
                </div>
                
                <div class="childComment" v-else-if='comment.parentId && !comment.recomment' style='display:flex'>                    
                    <img src="../../assets/cha2.png" alt="" height="40px">
                    <span>{{comment.userNick}} :</span> 
                    <span class='deleted' v-if='comment.isDeleted'>삭제된 대댓글입니다</span>
                    <span v-else>{{comment.content}}</span>
                    <div class='recommentStatus'>
                        <div style='display:flex'>
                            <span v-if='comment.userOwn && !comment.isDeleted' style='margin-left:10px'>수정</span>
                            <span v-if='comment.userOwn  && !comment.isDeleted' style='margin-left:10px' @click='deleteComment(idx,comment.commentNo)'>삭제</span>
                        </div>
                        

                    </div>
                </div>             
                <div v-if='comment.recomment' class='recommentDiv'>
                
                    <input class='recommentInput' type="text" v-model='recommentParams.content' @keypress.enter=createRecomment(recommentParentId,idx)>
                    <button @click='createRecomment(recommentParentId,idx)'>대댓글등록</button>
                </div>
            </div>
        </div>
        <input type="checkbox" style='margin-right:5px' @click='unknownAlert'><span style='margin-right:5px'>익명</span>
        <input type="text" class='commentinput' v-model='params.content' @keypress.enter='createComment(0)'>
        <button @click='createComment(0)'>댓글작성</button>
        
    </div>
</template>
<script>
import axios from 'axios'
import jwt_decode from 'jwt-decode'
const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'


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
        }
    },

    methods:{
        createComment:function(num){
            this.params.boardId = this.$route.params.boardNo
            this.params.parentId = num

            axios.post(`${SERVER_URL}/comment`,this.params,this.$store.getters.setToken)
                .then(res=>{
                    if(res.data.response==='error') {
                        alert('오류발생/로그아웃 후 재진행')
                        localStorage.removeItem('jwt')
                        this.$router.push({name:'Sign'})
                    } else if (this.params.content) {
                        const decode = jwt_decode(localStorage.getItem('jwt'))
                        console.log(res)
                        const param = {
                            'content':this.params.content,
                            'isUnknown':this.params.isUnknown,
                            'userNick': decode.user.userNick,
                            'parentId':this.params.parentId,
                            'userOwn':1,
                            'createdAt':'현재',
                        }
                        
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
            console.log(parentId,idx)
            this.recommentParams.boardId = this.$route.params.boardNo
            this.recommentParams.parentId = parentId
            axios.post(`${SERVER_URL}/comment`,this.recommentParams,this.$store.getters.setToken)
                .then(res=>{
                    if(res.data.response==='error') {
                        alert('오류발생/로그아웃 후 재진행')
                        localStorage.removeItem('jwt')
                        this.$router.push({name:'Sign'})
                    } else if (this.recommentParams.content) {
                        const decode = jwt_decode(localStorage.getItem('jwt'))
                        this.recommentParams.userNick = decode.user.userNick 
                        this.apiData.splice(idx,1)                       
                        this.apiData.splice(idx,0,this.recommentParams)
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
        unknownAlert:function(){
            alert('익명게시판은 현재 지원되지 않습니다.')
        },
        deleteComment:function(idx,commentIdx){

            axios.delete(`${SERVER_URL}/comment/${commentIdx}`,this.$store.getters.setToken)
                .then(res => {
                    if (res.data.response === 'error') {
                        alert(res.data.message)
                    } else {
                        // this.apiData.splice(idx,1)
                        this.apiData[idx].content = '삭제된 댓글입니다'
                        this.apiData[idx].isDeleted = 1
                        console.log(res)
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        },
        recomment:function(idx){
            this.recommentParams.content = ''
            let addCheckIdx = 0
            if (this.lastRecommentIdx !==-1){
                this.apiData.splice(this.lastRecommentIdx,1)
            }

            if (this.lastRecommentIdx!==-1 && this.lastRecommentIdx<idx){
                addCheckIdx = 1
            }

            this.lastRecommentIdx = -1

            const parentId = this.apiData[idx].commentNo
            let addIdx = 0
            const recommentInput = {
                recomment:1,
            }

            for (let i=0; i<this.apiData.length;i++){
                if (this.apiData[i].commentNo === parentId){
                    addIdx = i
                }
                if (this.apiData[i].parentId === parentId){
                    addIdx = i
                }
            }
            this.apiData.splice(addIdx+1+addCheckIdx,0,recommentInput)
            if (document.querySelector('.recommentInput')){
                document.querySelector('.recommentInput').focus()
            } 
            this.lastRecommentIdx = addIdx+1+addCheckIdx
            this.recommentParentId = parentId
            
        }
    },
    created(){
        const No = this.$route.params.boardNo
        axios.get(`${SERVER_URL}/comment/${No}`,this.$store.getters.setToken)
            .then(res=>{
                if (res.data.response==='error'){
                    alert('오류발생/로그아웃 후 재진행/머지')
                    // localStorage.removeItem('jwt')
                    // this.$router.push({name:'Sign'})
                    console.log(res)
                } else {
                    this.apiData = res.data.data 
                    console.log(this.apiData)
                }
            })
            .catch(err=>{
                console.log(err)
            })
    },
    computed:{
        // timdeDiff:function(date){
        //     const now = new Date()
        //     const timeValue = new Date(date)
        //     console.log(now,timeValue)
        //     return now


        // }
    }
}
</script>
<style>
#boardComment {
    font-family: font1;
    position:absolute;
    width: 60%;
    max-height: 900px;
    background-color: #A87A4F;
    top: 54%;
    left: 20%;  
    padding:20px;
    color:white;
    overflow: auto;
    overflow-x:hidden;
    text-align:center;
}
.commentlist {
    overflow: auto;
    overflow-x: hidden;
    text-align: left;
}

.parentComment {
    width: 85%;    
    border-bottom: rgb(255, 255, 255) 1px solid;
    margin-bottom: 10px;
    display:flex;
    justify-content: space-between;
    
  
}
.parentComment > span{
    margin-left: 10px;
}
.commentlist i {
    margin-left: 40px;
}


.commentLeft {
    display:flex;
    
}
.commentNick{
    margin-top: 5px;
    margin-left: 10px;
    white-space: nowrap;
}
.commentContent {
    margin-top: 5px;
    width: 770px;
}
.commentStatus {
    position:absolute;
    right:0;
    transform: translateX(10%);
    width:250px;
    text-align: left;
    height: 20px;
    font-size: 12px;
    cursor:pointer;
    
}

.recommentStatus{
    right:0;
    /* transform: translateX(10%); */
    width:250px;
    text-align: left;
    height: 20px;
    font-size: 12px;
    cursor:pointer;
}


.commentStatus > span {
    text-align: left;
    white-space: nowrap;
    
}


.commentContent {
    width: 100%;
}

.childComment {
    width:80%;
    margin-left: 5%;
    margin-bottom: 10px;
    border-bottom: beige 1px solid;
}

.recommentDiv {
    display:flex;
}

.recommentDiv > button {
    border:none;
    outline: none;
    border-radius: 4px;
    margin-right: 4px;
    height:40px;
    width:120px;
    margin-top: 20px;


}

.recommentInput{

    width:60%;
    margin: 20px 0 20px 20%;
    height: 40px;
    border:none;
    outline:none;
    text-align: left;
    border-radius: 10px;
}
.deleted {
    font-style: italic;
    text-decoration: line-through;
}

.childComment > span{
    margin-left: 10px;
}




.commentinput{
    margin-top: 20px;
    width:80%;
    outline: none;
    border:none;
    text-align: left;
    padding-right: 10px;
    height: 40px;
    border-radius: 10px;
}

#boardComment > button {
    border:none;
    outline: none;
    border-radius: 4px;
    margin-right: 4px;
    height:40px;
}



</style>