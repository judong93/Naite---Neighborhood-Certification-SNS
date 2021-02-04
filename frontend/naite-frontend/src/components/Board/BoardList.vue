<template>
    <div id="boardlist">
        <div class="writeBoard">
            <span class='writeBoardNext' @click='writeBoard'>글 쓰기 </span>
        </div>
        <div class="listBox"> 
            <div class="Box">
                <div v-for='(list,idx) in apiData' :key='idx'>
                    <div class='list'>                                      
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
                                    <div v-if='list.unKnownFlag'>익명</div>
                                    <div v-else>{{list.userNo}}님의 글</div>
                                </div>
                                <div>
                                    <i class="far fa-images"></i>
                                    이미지갯수보여주는식
                                    <i class="far fa-thumbs-up"></i>
                                    {{list.boardLikeCnt}}&nbsp;&nbsp;&nbsp;&nbsp;
                                    <i class="far fa-comment-dots"></i>
                                    {{list.boardReportCnt}}
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

const SERVER_URL = 'http://localhost:8080'

export default {
    name:'BoardList',
    components:{
                
    },
    data: function() {
        return {
            apiData: {},
        }
    },
    props:{
        bigCategoryNo: [Number,String],
    },
    methods:{
        loadList: function(){
            axios.get(`${SERVER_URL}/board/list/${this.bigCategoryNo}`)
                .then(res=>{
                    console.log(res.data)
                    this.apiData = res.data
                    console.log(res.data)
                })
                .catch(err=> {
                    console.log(err)
                })
        },
        writeBoard:function(){
            this.$router.push({name:'Posting'})
        }   
    },
    watch:{
        bigCategoryNo:function(){
            console.log('변경!')
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