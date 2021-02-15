<template>
    <div id="boardlist">
        <div class="writeBoard">
            <span class='writeBoardNext' @click='writeBoard'>글 쓰기 </span>
        </div>
        <div class='mobileBoardTitle'>
            <h3 >{{categoryName[bigCategoryNo]}}</h3>
            <span>{{mobileCategory[bigCategoryNo]}}</span>
        </div >
        <div class="listBox"> 
            <div class="Box">
                <div v-for='(list,idx) in apiData' :key='idx'>
                    <div class='list' @click='toDetail(list.boardNo)'>                                      
                        <div class='listInfo'>
                            <div class='listTitle'>
                                <div>
                                    {{list.boardTitle}}
                                </div>
                                <div class='listContent'>
                                    {{list.boardContent}}
                                </div>
                            </div>
                            
                            <div class='subList'>
                                <div class='listUser'>
                                    <div v-if='list.unknownFlag'>익명님이 {{categoryName[list.bigCategoryNo]}}에 남긴 글</div>
                                    <div v-else>{{list.userNick}}님이 {{categoryName[list.bigCategoryNo]}}에 남긴 글</div>
                                </div>
                                <div>
                                    <i class="far fa-images"></i>
                                    <!-- {{list.boardPic.length}} -->
                                    <i class="far fa-thumbs-up"></i>
                                    {{list.boardLikeCnt}}&nbsp;&nbsp;&nbsp;&nbsp;
                                    <i class="far fa-comment-dots"></i>
                                    {{list.boardCommentCnt}}
                                    <i class="far fa-clock"></i>
                                    {{list.boardCreatedAt}}
                                </div>
                            </div>
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
    name:'BoardList',
    components:{
                
    },
    data: function() {
        return {
            apiData: {},
            categoryName:['','번화가','동사무소','수군수군','소리소문','장터','확성기'],
            mobileCategory:['','우리동네 자유게시판','우리동네 질문게시판','수군수군','우리동네 중요사항','장터','우리동네 공지사항'],
            imgCnt:0,
        }
    },
    props:{
        bigCategoryNo: [Number,String],
    },
    methods:{
        loadList: function(){
            axios.get(`${SERVER_URL}/board/list/${this.bigCategoryNo}`,this.setToken())
                .then(res=>{
                    this.apiData = res.data                    
                })
                .catch(err=> {
                    console.log(err)
                })
        },
        writeBoard:function(){
            this.$router.push({name:'Posting'})
        } ,
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
        }
    },
    watch:{
        bigCategoryNo:function(){
            this.loadList()
        },
    },
    created:function(){
        this.loadList()
    }
}
</script>

<style>
#boardlist {
    position:relative;    
    font-family: font1;
    width:100%;
    height:100%;
}


.writeBoard {
    position:absolute;
    top: 24%;
    left:19.7%;
    width: 60%;
    height: 2%;
    text-align: right;
}

.writeBoardNext{
    cursor:pointer;
    font-size: 20px;
    background-color: #3F9F47;
    color:white;
    padding: 5px 15px;
    border-radius: 10px;
    
}


.listBox{
    position:absolute;
    width: 60%;
    height: 70%;
    /* background-color:#e5c09d; */
    background-color:#Ffffff;
    left: 20%;
    border-radius: 20px;
    top: 28%; 
    transition: 0.3s;
    overflow:auto;
    overflow-x: hidden;
    border: 1px solid rgb(224, 224, 224);
    display:flex;
}

.Box {
    width: 100%;
    height: 100%;
    border-right: 0.1px solid rgb(255, 255, 255);
    padding-top: 10px;
}


.list {   
    display:flex;
    /* border-bottom: 1px solid #ffffff; */
    border-bottom: 1px solid #6e6e6e;
    /* width:80%; */
    width:100%;
    justify-content: space-between;
    /* margin-left: 100px; */
    margin-left: 20px;
    cursor: pointer;
}

.subList{    
    justify-content: space-between;
    /* width: 550px; */
    height:55px;
    /* width:300px; */
    width:480px;
}

.listInfo {
    margin:10px;
    display:flex;
}



.listTitle{
    font-size: 23px;
    text-align: left;
    width:600px;
    height:55px;
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}


.listContent{
    font-size: 14px;
    margin: 5px 0;
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    
}


.subList {
    /* font-size: 10px; */
    font-size: 12px;
    text-align: right;
}

.listUser {
    height:37px;
    font-size: 15px;
}
.mobileBoardTitle{
    display: none;
}


@media screen and (max-width: 501px) {
    #boardlist{
        width:100vw;
        height: 100vh;
        text-align: left;
     
    }
    .mobileBoardTitle{
        position:relative;
        display: initial;
        text-align: left;
        top:10%;
        left:0;
        margin-left: 5%;
        display: flex;

    }
    .mobileBoardTitle > span {
        margin-left: 5%;
        margin-top: 2%;
    }
    .writeBoard {        
        display: none;
    }

    .listBox{
        width: 96%;
        height: 100%;
        /* background-color:#e5c09d; */
        /* background-color: blue; */
        /* background-color:transparent; */
        left: 0%;
        border-radius: 0px;
        margin-left: 2%;
        top: 15%;         
        border: none;
        display:flex;
        padding:2%;
        overflow: auto;
        
    }

    .Box {
        width: 100%;
        height: 80%;
        
        border-right: 0.1px solid rgb(255, 255, 255);
        padding-top: 0px;
        overflow: auto;
    }


    .list {   
        display:flex;
        /* border-bottom: 1px solid #ffffff; */
        border-bottom: 1px solid #6e6e6e;
        /* width:80%; */
        width:100%;
        justify-content: space-between;
        /* margin-left: 100px; */
        margin-left: 0px;  
        cursor: pointer;
    }
    .listInfo {
        padding:2%;
        width: 100%;   
        margin:0;     
    }

    .subList{    
        justify-content: space-between;
        height:100%;
        width:50%;        
    }




    .listTitle{
        font-size: 15px;
        text-align: left;        
        height:55px;
        overflow:hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .listTitle :nth-child(1){
        width:200px;
        overflow:hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }


    .listContent{
        font-size: 10px;
        margin: 8% 0 0 0 ;
        width: 180px;
        overflow:hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        
    }


    .subList {
        /* font-size: 10px; */
        font-size: 10px;
        text-align: right;
        margin-right: 10px;
    }

    .listUser {        
        font-size: 10px;
    }

    .listUser + div {        
        white-space: nowrap;
    }

    
}


</style>