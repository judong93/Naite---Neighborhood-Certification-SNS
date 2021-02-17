<template>
    <div id="reviewboardlist">
        <div class="reviewwriteBoard">
            <ReviewCategory @changeSmallCategory='changeSmallCategory'/>
            <span class='reviewwriteBoardNext' @click='writeBoard'>글 쓰기 </span>
        </div>
        <div class='reviewmobileBoardTitle'>
            <h3 >수군수군</h3>
            <span>우리동네 리뷰게시판</span>
        </div >
        <div class="reviewlistBox"> 
            <div class="reviewBox">
                <div v-for='(list,idx) in apiData' :key='idx'>                    
                    <div class='reviewlist' @click='toDetail(list.reviewNo,list.board.boardNo)'>                                      
                        <div class='reviewlistInfo'>
                            <div class='reviewlistTitle'>
                                <div>
                                    {{list.board.boardTitle}}
                                </div>
                                <div class='reviewlistContent'>
                                    {{list.board.boardContent}}
                                </div>
                            </div>
                            
                            <div class='reviewsubList'>                                
                                <div class='reviewlistUser'>                                    
                                    <div>{{list.userNick}}님이 {{smallCategoryNmae[list.smallCategoryNo]}}에 대해 {{categoryName[list.board.bigCategoryNo]}}
                                        <span v-if='list.smallCategoryNo===1'>&#xf2e7;</span>
                                        <span v-if='list.smallCategoryNo===2'>&#xf0f9;</span>
                                        <span v-if='list.smallCategoryNo===3'>&#xf70c;</span>
                                        <span v-if='list.smallCategoryNo===4'>&#xf0c4;</span>
                                        <span v-if='list.smallCategoryNo===5'>&#xf563;</span>
                                    </div>
                                </div>
                                <div>                                    
                                    <i class="far fa-images"></i>
                                    {{list.files ? list.files.length:0}}
                                    <i class="far fa-thumbs-up"></i>
                                    {{list.board.boardLikeCnt}}&nbsp;&nbsp;&nbsp;&nbsp;
                                    <i class="far fa-comment-dots"></i>
                                    {{list.boardCommentCnt}}
                                    <i class="far fa-star"></i>
                                    {{list.reviewStar}}점
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
import ReviewCategory from '@/components/ReviewBoard/ReviewCategory'
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'
// const SERVER_URL = 'http://i4a402.p.ssafy.io:8080'

export default {
    name:'ReviewBoardList',
    components:{
        ReviewCategory,                
    },
    data: function() {
        return {
            apiData: {'board':{'board':{}}},
            categoryName:['','번화가','동사무소','수군수군','소리소문','장터'],
            smallCategoryNmae:['','식당','의료','체육시설','미용','기타'],
            imgCnt:0,           
        }
    },
    props:{
        bigCategoryNo: [Number,String],
    },
    methods:{
        loadList: function(){
            axios.get(`${SERVER_URL}/review/list`,this.setToken())
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
        toDetail:function(num,boardNo){
            this.$router.push({name:'ReviewBoardDetail',params:{reviewNo:num,boardNo:boardNo}})
        },
        changeSmallCategory:function(smallNo){
            if (smallNo !== 0 && smallNo!==-1) {
                axios.get(`${SERVER_URL}/review/list/${smallNo}`,this.setToken())
                    .then(res=>{
                        this.apiData = res.data  
                        console.log(this.apiData) 
                    })
                    .catch(err=> {
                        console.log(err)
                    })
            } else {
                this.loadList()
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
#reviewboardlist {
    position:relative;    
    font-family: font1;
    width:100%;
    height:100%;
}

.reviewwriteBoard {
    position:absolute;
    top: 24%;
    left:19.7%;
    width: 60%;
    height: 2%;
    text-align: right;
    
    
}

.reviewwriteBoardNext{
    cursor:pointer;
    font-size: 20px;
    background-color: #3F9F47;
    color:white;
    padding: 5px 15px;
    border-radius: 10px;
    
}


.reviewlistBox{
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
.reviewlistBox::-webkit-scrollbar { width: 10px;}
.reviewlistBox::-webkit-scrollbar-track { background-color:rgba(0, 0, 0, 0.5);border-radius: 10px;  }
.reviewlistBox::-webkit-scrollbar-thumb { background: #e6e3e0f5;border-radius: 10px;  }
.reviewlistBox::-webkit-scrollbar-thumb:hover { background: #e68c42; } 
.reviewlistBox::-webkit-scrollbar-thumb:active { background: #e68c42; }
.reviewlistBox::-webkit-scrollbar-button { display: none; } 

.reviewBox {
    width: 100%;
    height: 100%;
    border-right: 0.1px solid rgb(255, 255, 255);
    padding-top: 10px;
}


.reviewlist {   
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

.reviewsubList{    
    justify-content: space-between;
    /* width: 550px; */
    height:55px;
    /* width:300px; */
    width:480px;
}

.reviewsubList i {
    margin-left: 5px;
}

.reviewsubList span {
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;    
}

.reviewlistInfo {
    margin:10px;
    display:flex;
}



.reviewlistTitle{
    font-size: 23px;
    text-align: left;
    width:600px;
    height:55px;
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}


.reviewlistContent{
    font-size: 14px;
    margin: 5px 0;
    overflow:hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    
}


.reviewsubList {
    /* font-size: 10px; */
    font-size: 12px;
    text-align: right;
}

.reviewlistUser {
    height:37px;
    font-size: 15px;
}

@media screen and (max-width:501px) {
    #reviewboardlist{
        width:100vw;
        height: 100vh;
        text-align: left;
     
    }
    .reviewmobileBoardTitle{
        position:relative;
        display: initial;
        text-align: left;
        top:10%;
        left:0;
        margin-left: 5%;
        display: flex;

    }
    .reviewmobileBoardTitle > span {
        margin-left: 5%;
        margin-top: 2%;
    }
    .reviewwriteBoard {        
        display: none;
    }

    .reviewlistBox{
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

    .reviewBox {
        width: 100%;
        height: 80%;
        
        border-right: 0.1px solid rgb(255, 255, 255);
        padding-top: 0px;
        overflow: auto;
    }


    .reviewlist {   
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
    .reviewlistInfo {
        padding:2%;
        width: 100%;   
        margin:0;     
    }

    .reviewsubList{    
        justify-content: space-between;
        height:100%;
        width:50%;        
    }




    .reviewlistTitle{
        font-size: 15px;
        text-align: left;        
        height:55px;
        overflow:hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }

    .reviewlistTitle :nth-child(1){
        width:200px;
        overflow:hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }


    .reviewlistContent{
        font-size: 10px;
        margin: 8% 0 0 0 ;
        width: 180px;
        overflow:hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        
    }


    .reviewsubList {
        /* font-size: 10px; */
        font-size: 10px;
        text-align: right;
        margin-right: 10px;
    }

    .reviewlistUser {        
        font-size: 10px;
    }

    .reviewlistUser + div {        
        white-space: nowrap;
    }

}



</style>