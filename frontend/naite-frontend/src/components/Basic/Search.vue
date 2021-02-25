<template>
    <div id='search'>
        <input type="text" @click="changeState" v-model='searchContent' @input="enterSearch" @keypress.enter='enterSearch' placeholder="제목을 통해 게시물을 검색하세요!">
        <div v-if='apiData'>
            <div v-for='(board,idx) in apiData' :key='idx' @click='showDetailBoard(board)'>                  
                <header>
                    <img :src="board.userPic" alt="" width='60px' height="60px" style='border:1px solid white; border-radius:10px'>
                    <div>
                        <div>
                            {{board.boardTitle}}
                        </div>
                        <div>
                            {{board.boardContent}}
                        </div>
                    </div>
                </header>
                <footer>
                    <div>
                        <i class="far fa-images"></i>
                        {{board.files.length}}
                        <i class="far fa-thumbs-up"></i>
                        {{board.boardLikeCnt}}&nbsp;&nbsp;&nbsp;&nbsp;
                        <i class="far fa-comment-dots"></i>
                        {{board.boardCommentCnt}}
                    </div>
                    <div>
                        <i class="far fa-clock"></i>
                        {{board.boardCreatedAt}}    
                    </div>
                    <div>
                        {{ board.unknownFlag ? 익명:board.userNick}}님이 {{categoryName[board.bigCategoryNo]}}에 남긴 글
                    </div>

                </footer>
                

            </div>
        </div>
    </div>
</template>
<script>
import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL


export default {
    name:'Search',
    data:function(){
        return {
            clicked:false,
            searchContent:'',
            apiData:'',
            categoryName:['','번화가','동사무소','수군수군','소리소문','장터']
        }
    },
    created(){
        setTimeout(() => {
            const searchInput = document.querySelector('#search > input')
            searchInput.style.display='initial'
        }, 500);
    },
    methods:{
        changeState:function(){
            this.clicked = true
        },
        enterSearch:function(event){
            this.searchContent = event.target.value
            if(!this.searchContent){
                this.apiData = {}
            }

            axios.get(`${SERVER_URL}/board/list/title/${this.searchContent}`,this.setToken())
                .then(res => {
                    this.apiData = res.data
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
        showDetailBoard:function(board){
            if (board.bigCategoryNo === 3) {
                this.$router.push({name:'ReviewBoardDetail',params:{reviewNo:board.reviewNo,boardNo:board.boardNo}})
            } else if (board.bigCategoryNo === 5) {
                this.$router.push({name:'MarketBoardDetail',params:{marketNo:board.marketNo}})
            } else {
                this.$router.push({name:'BoardDetail',params:{boardNo:board.boardNo}})
            }
        }
    },
    watch:{
        clicked:function(){
            const searchInput = document.querySelector('#search > input')
            if(this.clicked) {
                searchInput.style.top ='15%'
            } else {
                searchInput.style.top ='40%'
            }
        }
    }
}
</script>
<style>

#search {
  position:relative;
  height: 100vh;   
  width:100vw;  
  overflow:auto;
  background-color:  rgb(250, 246, 240);
}

#search > input {
    position:fixed;
    top:40%;
    left:50%;
    width: 700px;
    height: 50px;
    background-color: #A87A4F;
    transform: translate(-50%,-50%);
    z-index: 100;
    border:none;
    border-radius: 10px;
    outline: none;
    font-family: font1;
    color:white;
    display: none;
    transition: 0.3s;
}


#search > input+div {
    position:relative;
    top:25%;
    color:black;
    left: 25%;
    width:50%;
    max-height:74%;
    overflow: auto;
    overflow-x:hidden;
    border: 1px solid black;
    border-radius: 10px;
    background-color: #a87a4f1c;
    
}

#search > input+div > div{
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
    border-bottom: 1px solid #a87a4f4d;
    padding: 3px;
    background-color: #b9e67e;
    color:rgb(109, 109, 109);
    cursor:pointer;
}

#search >input+div>div>header {
    display:flex;
    font-family: font1;
    text-align: left;
}
#search >input+div>div>header>div{
    margin-left: 10px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    padding:2px 5px;
    max-width: 700px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
}
#search >input+div>div>header>div>div:nth-child(1){
    font-size: 18px;
}
#search >input+div>div>header>div>div:nth-child(2){
    font-size: 12px;
    overflow: hidden;
    max-width: 600px;
    text-overflow: ellipsis;

}
#search > div > div >footer {
    font-size: 1px;
    display:flex;
    flex-direction: column;
    justify-content: space-between;
    text-align: right;
    padding:2px 5px;
    font-family: font1;
    white-space: nowrap;
}



@media screen and (max-width:501px) {
    #search > input {
        position:fixed;
        top:40%;
        left:50%;
        width: 80%;
        height: 50px;
        font-size: 13px;
    }


    #search > input+div {
        position:relative;
        top:22%;
        color:black;
        left: 0%;
        width:99%;
        max-height:71%;
        border-radius: 0px;
        
    }

    #search > input+div > div{       
        margin-bottom: 5px;
        border-bottom: 1px solid #a87a4f4d;
        padding: 2px;
    }

    #search >input+div>div>header>div{
        margin-left: 5px;
        padding:2px 5px;
        max-width: 200px;       
    }
    #search >input+div>div>header>div>div:nth-child(1){
        font-size: 14px;
        max-width: 200px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
    }
    #search >input+div>div>header>div>div:nth-child(2){
        font-size: 10px;
        max-width: 200px;
    }
    #search > div > div >footer {
        font-size: 8px;
        display:flex;
        flex-direction: column;
        justify-content: space-between;
        text-align: right;
        padding:2px 6px 2px 0px ;
        font-family: font1;
        white-space: nowrap;
}


    
}





</style>