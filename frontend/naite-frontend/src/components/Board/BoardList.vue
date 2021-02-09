<template>
    <div id="boardlist">
        <div class="writeBoard">
            <span class='writeBoardNext' @click='writeBoard'>글 쓰기 </span>
        </div>
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
                                    {{list.boardPic.length}}
                                    <i class="far fa-thumbs-up"></i>
                                    {{list.boardLikeCnt}}&nbsp;&nbsp;&nbsp;&nbsp;
                                    <i class="far fa-comment-dots"></i>
                                    {{list.boardCommentCnt}}
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
            categoryName:['','번화가','동사무소','수군수군','소리소문','장터'],
            imgCnt:0,
        }
    },
    props:{
        bigCategoryNo: [Number,String],
    },
    methods:{
        loadList: function(){
            axios.get(`${SERVER_URL}/board/list/${this.bigCategoryNo}`)
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