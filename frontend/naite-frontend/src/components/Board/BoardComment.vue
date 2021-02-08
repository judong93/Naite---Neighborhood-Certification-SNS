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
                        <div>
                            <i class="fas fa-share"></i><span>대댓글 달기</span>
                            <span v-if='comment.userOwn && !comment.isDeleted' style='margin-left:10px'>수정</span>
                            <span v-if='comment.userOwn  && !comment.isDeleted' style='margin-left:10px' @click='deleteComment(idx,comment.commentNo)'>삭제</span>
                        </div>
                        

                    </div>
                </div>
                
                <div class="childComment" v-else>                    
                    <img src="../../assets/cha2.png" alt="" height="40px">
                    <span>{{comment.userNick}} :</span> 
                    <span>{{comment.content}}</span>
                    
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
            apiData:[],
            reComment:false,
            
        }
    },

    methods:{
        createComment:function(num){
            this.params.boardId = this.$route.params.boardNo
            this.params.parentId = num
            axios.post(`${SERVER_URL}/comment`,this.params,this.setToken())
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
                            'parentId':0,
                            'userOwn':1,
                            'createdAt':'현재',
                        }
                        this.apiData.push(param)
                        this.params.content=''
                    } else {
                        console.log(res)
                        console.log(this.params.content)
                        alert('내용물을 작성해주세요')
                    }

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
        },
        unknownAlert:function(){
            alert('익명게시판은 현재 지원되지 않습니다.')
        },
        deleteComment:function(idx,commentIdx){
            console.log(idx,'삭제')
            axios.delete(`${SERVER_URL}/comment/${commentIdx}`,this.setToken())
                .then(res => {
                    if (res.data.response === 'error') {
                        alert(res.data.message)
                    } else {
                        // this.apiData.splice(idx,1)
                        this.apiData[idx].content = '삭제된 댓글입니다'
                        console.log(res)
                    }
                })
                .catch(err=>{
                    console.log(err)
                })
        }
    },
    created(){
        const No = this.$route.params.boardNo
        axios.get(`${SERVER_URL}/comment/${No}`,this.setToken())
            .then(res=>{
                if (res.data.response==='error'){
                    alert('오류발생/로그아웃 후 재진행')
                    localStorage.removeItem('jwt')
                    this.$router.push({name:'Sign'})
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