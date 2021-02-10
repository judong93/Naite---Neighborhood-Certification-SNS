<template>
    <div id="boardlist">
        <div class="writeBoard">
            <ReviewCategory @changeSmallCategory='changeSmallCategory'/>
            <span class='writeBoardNext' @click='writeBoard'>글 쓰기 </span>
        </div>
        <div class="listBox"> 
            <div class="Box">
                <div v-for='(list,idx) in apiData' :key='idx'>                    
                    <div class='list' @click='toDetail(list.reviewNo,list.board.boardNo)'>                                      
                        <div class='listInfo'>
                            <div class='listTitle'>
                                <div>
                                    {{list.board.boardTitle}}
                                </div>
                                <div class='listContent'>
                                    {{list.board.boardContent}}
                                </div>
                            </div>
                            
                            <div class='subList'>                                
                                <div class='listUser'>                                    
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
                                    {{list.board.boardPic ?list.board.boardPic.length:0 }}                                    
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
            axios.get(`${SERVER_URL}/review/list`)
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
                axios.get(`${SERVER_URL}/review/list/${smallNo}`)
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
    font-family: font1;
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

.subList i {
    margin-left: 5px;
}

.subList span {
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;    
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

</style>