<template>
  <div id="market-board">
    
    <BoardImg :message='message' :imgsrc='imgsrc'/>
    <MarketCategory @changeSmallCategory='changeSmallCategory' />
    <div class='marketmobileBoardTitle'>
        <span>우리동네 공동구매/중고거래 </span>
        <h3 >장터</h3>
    </div >
    <div class="button-list">
      <select name="카테고리" v-model="smallCategoryNo">
        <option value="6" selected>공동구매</option>
        <option value="7">중고거래</option>
      </select>
      <span class='write-button' @click='writeBoard'>글 쓰기 </span>
    </div>
    <div class="group-buying">
      <div @click="toMarketDetail(posting.marketNo)" v-for="(posting, idx) in marketPostings" :key="idx" class="posting-box">
        <img :src="posting.files.length>0 ? posting.files[0]:thumbnail" alt="" class="posting-img">
        <div class="posting-card">  
          <div  class="posting-title">
            {{ posting.board.boardTitle }}
          </div>
          <div class='posting-content'>
            {{posting.board.boardContent}}
          </div>
          <div class="area-created">
            <div>
              가격: {{ posting.marketCost }}원
            </div>
            <div>
              <i class="fas fa-user"></i>
              {{posting.userNick}}님
              <i class="fas fa-surprise"  v-if='userScore[idx]>66' style='color:green;opacity:0.7'></i>              
              <i class="fas fa-smile-beam" v-else-if='userScore[idx]>40'  style='color:blue;opacity:0.5'></i>
              <i class="fas fa-meh" v-else-if='userScore[idx]>25'  style='color:orange;opacity:0.5;'></i>
              <i class="fas fa-sad-cry" v-else style='color:red;opacity:0.7'></i>
              <span>신뢰도 {{userScore[idx]}}점</span>
              <i class="far fa-clock"></i>
              {{ posting.boardCreatedAtSimple }}
            </div>

          </div>
        </div>
      </div>
    </div>


    
  </div>
</template>

<script>
import BoardImg from '@/components/Board/BoardImg'
import MarketCategory from '@/views/MarketBoard/MarketCategory'
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: "MarketBoard",
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: require(`../../assets/5.jpg`),
      onDetail: false,
      thumbnail: "https://picsum.photos/200/300",
      marketPostings: [],
      smallCategoryNo: 6,
      userScore:[],
    }
  },
  components: {
    BoardImg,
    MarketCategory
  },
  methods: {
    changeSmallCategory:function(num){
      this.smallCategoryNo = num
    },
    writeBoard:function(){
      this.$router.push({name:'Posting'})
    },
    toMarketDetail: function (marketNo) {
      this.$router.push({name:'MarketBoardDetail',params:{marketNo:marketNo}})
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
  },
  created: function () {
    axios.get(`${SERVER_URL}/market/list/6/`, this.setToken())
      .then((res) => {
        this.marketPostings = res.data
        this.userScore=[]
          for (let i=0;i<res.data.length;i++){
            axios.get(`${SERVER_URL}/user/profile/${res.data[i].board.userNo}`, this.setToken())
              .then(res=>{
                this.userScore.push(res.data.data.userScore)
              })
              .catch(err=>{
                console.log(err)
              })
          }
      })
      .catch((err) => {
        console.log(err)
      })
  },
  watch: {
    smallCategoryNo: function () {
      const smallCategoryNo = this.smallCategoryNo
      axios.get(`${SERVER_URL}/market/list/${smallCategoryNo}/`, this.setToken())
        .then((res) => {
          this.marketPostings = res.data
          this.userScore=[]
          for (let i=0;i<res.data.length;i++){
            axios.get(`${SERVER_URL}/user/profile/${res.data[i].board.userNo}`, this.setToken())
              .then(res=>{
                this.userScore.push(res.data.data.userScore)
              })
              .catch(err=>{
                console.log(err)
              })
          }
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }, 
}
</script>

<style scoped>
#market-board {
  position:relative;
  width: 1920px;
  height: 969px;
  overflow:hidden;
  background-color: rgb(250, 246, 240);
  font-family: font1;
}
.button-list {
  position:absolute;
  top: 24%;
  left:59.5%;
  width: 20%;
  height: 2%;
  text-align: right;
}
.write-button {
  cursor:pointer;
  font-size: 20px;
  background-color: #3F9F47;
  color:white;
  padding: 5px 15px;
  border-radius: 10px;
}
.group-buying {
  position: absolute;
  /* display: flex; */
  /* flex-wrap: wrap; */
  overflow: auto;
  top: 29%;
  max-height:70%;
  width: 60%;
  left: 20%;
  right: 20%;
  text-align: left;
}
.vl {
  border: 1px solid lightgray;
  height: 500px;
  position: absolute;
  left: 50%;
  top: 300px;
}
.posting-box {
  position: relative;
  display: inline-block;
  overflow: hidden;
  height: 110px;
  width: 545px;
  margin-right: 20px;
  margin-bottom: 10px;
  border-bottom: 2px solid lightgray;
  left: 0;
  cursor: pointer;
}
.posting-card {
  display: inline-block;
  /* margin-left: 20px; */
}
.posting-img {
  position: absolute;
  display: inline-block;
  height: 93%;
  width: 19.5%;
  overflow: hidden;
  left: 10px;
  border-radius: 10%;
  margin-bottom: 15px;
}
.posting-title {
  position: absolute;
  overflow: hidden;
  cursor: pointer;
  max-width: 70%;
  top: 1%;
  font-size: 20px;
  left: 25%;
  white-space: nowrap;
  text-overflow: ellipsis;
  text-align: left;  
}
.posting-content{
  position: absolute;
  overflow: hidden;
  cursor: pointer;
  max-width: 70%;
  top: 35%;
  font-size: 14px;
  left: 25%;
  white-space: nowrap;
  text-overflow: ellipsis;
  text-align: left;
  color:rgb(104, 102, 102);
}
.area-created {
  position: absolute;
  display: flex;
  justify-content: space-between;
  align-items:flex-end;
  bottom: 7px;
  left: 25%;
  width: 75%;
  font-size:14px;
  color:rgb(104, 102, 102);
}
.area-created > div:nth-child(2)>i{
  margin-left: 10px;
  font-size: 16px;
  margin-right: 2px;

}
.created {
  /* position: absolute; */
  margin-left: 60px;
}
.market-thumbs {
  margin-left: 40px;
}
.market-comments {
  margin-left: 17px;
}

.button-list>select {
  display: none;
}
.marketmobileBoardTitle{
  display:none;
}

@media screen and (max-width: 501px) {
  /* #market-board {
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .button-list>select {
    display: initial;
  }
  .group-buying {
    position: inherit;
    width: 80%;
    right: 0;
    left: 0;
    top: 27%;
  }
  .group-buying::-webkit-scrollbar {
    display: none;
  }
  .posting-img {
    height: 95px;
    width: 95px;
  }
  .posting-title {
    left: 30%;
    font-size: 10px;
  }
  .posting-box {
    width: 100%;
  }
  .posting-box:hover {
    opacity: 0.5;
  }
  .area-created {
    left: 30%;
  }
  .button-list {
    position: inherit;
    width: 80%;
    text-align: right;
    left: 0;
  }
  .write-button {
    display: none;
  } */
  #market-board {
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .button-list>select {
    display: block;
    border:none;
    outline:none;
    background-color: rgba(230, 160, 81, 0.664);
    border-radius: 10px;
    padding:2px;
    color:white;
  }
  .button-list {
    position:absolute;
    top: 10%;
    left:3%;
    width: 10%;
    height: 5%;
    text-align: right;
    
  }
  .write-button {
    display:none;
  }
  .group-buying {
    position: absolute;
    /* display: flex; */
    /* flex-wrap: wrap; */
    overflow: auto;
    top: 14%;
    max-height:79%;
    width: 100%;
    left: 0%;
    border-top: 1px solid black;
    padding-top: 10px;
    
    text-align: left;
  }
  .vl {
    border: 1px solid lightgray;
    height: 500px;
    position: absolute;
    left: 50%;
    top: 300px;
  }
  .posting-box {
    position: relative;
    display: inline-block;
    overflow: hidden;
    height: 90px;
    width: 100%;
    margin-right: 20px;
    margin-bottom: 10px;
    border-bottom: 2px solid lightgray;
    left: 0;
    cursor: pointer;
  }
  .posting-card {
    display: inline-block;
    /* margin-left: 20px; */
  }
  .posting-img {
    position: absolute;
    display: inline-block;
    height: 93%;
    width: 19.5%;
    overflow: hidden;
    left: 10px;
    border-radius: 10%;
    margin-bottom: 15px;
  }
  .posting-title {
    position: absolute;
    overflow: hidden;
    cursor: pointer;
    max-width: 70%;
    top: 1%;
    font-size: 16px;
    left: 25%;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: left;  
  }
  .posting-content{
    position: absolute;
    overflow: hidden;
    cursor: pointer;
    max-width: 70%;
    top: 35%;
    font-size: 12px;
    left: 25%;
    white-space: nowrap;
    text-overflow: ellipsis;
    text-align: left;
    color:rgb(104, 102, 102);
  }
  .area-created {
    position: absolute;
    display: flex;
    justify-content: space-between;
    align-items:flex-end;
    bottom: 7px;
    left: 25%;
    width: 70%;
    font-size:10px;
    color:rgb(104, 102, 102);
  }
  .area-created > div:nth-child(2)>i{
    margin-left: 10px;
    font-size: 16px;
    margin-right: 2px;

  }
  .created {
    /* position: absolute; */
    margin-left: 60px;
  }
  .market-thumbs {
    margin-left: 40px;
  }
  .market-comments {
    margin-left: 17px;
  }

  .marketmobileBoardTitle{
    display:block;
    position:absolute;
    top:9%;
    display:flex;
    right:5%;
    
  }
  .marketmobileBoardTitle > span {
    margin-top: 10px;
  }
  .marketmobileBoardTitle >h3{
    margin-left:10  px
  }
 

}
</style>