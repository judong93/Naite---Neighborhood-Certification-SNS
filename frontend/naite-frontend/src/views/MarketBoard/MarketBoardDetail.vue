<template>
  <div id="marketboarddetail">
    <BoardImg :message='message' :imgsrc='imgsrc'/>
    <div class="market-detail-box">
      <div class="market-detail-img">
        <div class="market-detail-lg-img-container">
          <img :src="marketDetailContent.files.length>0 ? marketDetailContent.files[this.selectedMarketImgNo]: require('../../assets/이미지없을시.jpg')" class="market-detail-lg-img" alt="이미지가 없습니다">
        </div>
        <div class="market-detail-sm-img-container">

          <img @click="changeMarketPic(idx)" :src="marketImg" v-for="(marketImg, idx) in marketDetailContent.files" :key="idx" alt="" class="market-detail-sm-img">
        </div>
      </div>
      <div class="market-detail-content-container">
        <div v-if="marketDetailContent.board" class="market-detail-content-title">{{marketDetailContent.board.boardTitle}}</div>
        <div class="market-detail-subs">
          <div class="subs-title">
            <p>작성자</p>
            <p>작성시각</p>
            <p>가격</p>
            <p>상세내용</p>
          </div>
          <div class="subs-content">
            <div class="market-detail-writer">              
              <p @click="toWriterProfile">{{marketDetailContent.userNick}}</p>
              <i @click="chatWithPoster" class="far fa-comment-dots fa-2x"><span>작성자와 채팅하기</span></i>
            </div>
            <p>{{marketDetailContent.boardCreatedAt}}</p>
            <p>{{marketDetailContent.marketCost}}원</p>
          </div>
        </div>
        <div v-if="marketDetailContent.board" class="market-detail-boardcontent">
          {{ marketDetailContent.board.boardContent }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BoardImg from '@/components/Board/BoardImg'
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'MarketBoardDetail',
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: require(`../../assets/5.jpg`),
      imgData: require("../../assets/firstpage.jpg"),
      marketNo: 0,
      selectedMarketImgNo: 0,
      marketDetailContent: [],
    }
  },
  components: {
    BoardImg,
  },
  methods: {
    setToken:function(){
      const token=localStorage.getItem('jwt')
      const config = {
          headers: {
          'auth-token':`${token}`
          }
      }
      console.log(config)
      return config
    },
    toWriterProfile: function () {
      this.$router.push({name: 'Profile', params:{userNo: this.marketDetailContent.board.userNo}})
    },
    chatWithPoster: function () {
      const userNick = this.marketDetailContent.userNick
      axios.post(`${SERVER_URL}/market/join/${this.marketNo}/`, {}, this.setToken())
        .then(() => {
            axios.post(`${SERVER_URL}/chat/room?otherNick=${userNick}`,{},this.setToken())
                  .then(res=>{
                      this.$emit('sendMessageDirect',res.data,userNick)                    
                  })
                  .catch(err=>{
                      console.log(err)
                  })
        })
        .catch((err) => {
          console.log(err)
        })
    },
    changeMarketPic: function (idx) {
      console.log('잘 찎힘')
      this.selectedMarketImgNo = idx
    }
  },
  created: function () {
    const marketNo = this.$route.params.marketNo
    this.marketNo = marketNo
    axios.get(`${SERVER_URL}/market/detail/${marketNo}/`)
      .then((res) => {
        this.marketDetailContent = res.data
        console.log(this.marketDetailContent)
      })
      .catch((err) => {
        console.log(err)
      })
  }
}
</script>

<style scoped>
#marketboarddetail {
  position:relative;
  width: 1920px;
  height: 969px;
  overflow:hidden;
  background-color: rgb(250, 246, 240);
  font-family: font1;
}
.market-detail-box {
  position: absolute;
  display: flex;
  top: 26%;
  /* border: solid 3px red; */
  height: 650px;
  width: 54%;
  left: 23%;
}
.market-detail-img {
  position: relative;
  overflow: hidden;
  /* border: solid 3px red; */
  width: 43%; 
}
.market-detail-content-container {
  display: flex;
  flex-direction: column;
  /* border: solid 3px greenyellow; */
  width: 57%;
  padding-left: 5%;
  text-align: left;
}
.market-detail-lg-img-container {
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 70%;
  /* margin-top: 10%; */
}
.market-detail-lg-img {
  left: 0;
  position: absolute;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.market-detail-sm-img-container {
  position: relative;
  height: 10%;
  width: 100%;
  margin-top: 8%;
  text-align: left;
}
.market-detail-sm-img {
  position: relative;
  height: 100%;
  width: 15%;
  /* margin-top: 5%; */
  margin-right: 1.6%;
  cursor: pointer;
}
.market-detail-sm-img:hover {
  opacity: 0.5;
}
.market-detail-content-title {
  border-bottom: 1px solid rgb(201, 197, 197);
  padding-bottom: 10px;
  font-size: 25px;
  margin-bottom: 20px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.market-detail-subs {
  display: flex;
}
.subs-title {
  position: relative;
  color: rgb(184, 182, 182);
  width: 23%;
  padding-top: 5px;
}
.subs-content {
  position: relative;
  /* display: flex; */
  width: 70%;
  /* padding-top: 5px; */
  /* margin-right: 100px; */
}
.market-detail-writer {
  display: flex;
}
.market-detail-writer > p {
  padding-top: 5px;
  cursor: pointer;
}
.fa-comment-dots {
  position: relative;
  margin-left: 30px;
  /* padding-bottom: 20px; */
  /* padding-top: 0; */
}
.market-detail-boardcontent {
  margin-top: 4px;
  height: 34%;
  width: 100%;
  overflow: auto;
  padding: 1% 1% 1% 0;
}
.far {
  cursor: pointer;
}
.far:hover span {
  display: inline-block;
}
.far span {
  display: none;
  font-size: 15px;
  font-family: font1;
}
@media screen and (max-width: 501px) {
  #marketboarddetail {
    width: 100vw;
    height: 100vh;
    display: flex;
    justify-content: center;
  }
  .market-detail-box {
    flex-direction: column;
    align-items: center;
    position: inherit;
    top: 7%;
    width: 100%;
    left: 0;
    overflow: hidden;
  }
  .market-detail-img {
    width: 100%;
    height: 35%;
    overflow: visible;
  }
  .market-detail-lg-img-container {
    height: 100%;
    width: 100%;
    background-color: gray;
  }
  .market-detail-content-container {
    width: 90%;
    margin-top: 3%;
    padding-left: 0;
  }
  .market-detail-sm-img-container {
    margin-top: 0;
    height: 15%;
    display: none;
  }
  .market-detail-sm-img {
    margin-top: 15px;
    /* width: ; */
  }
  .market-detail-content-title {
    padding-bottom: 5%;
    margin-bottom: 3%;
  }
  .market-detail-boardcontent {
    position: relative;
    height: 30%;
    margin-top: 0;
  }
  .subs-title p {
    margin-bottom: 12px;
  }
  .subs-content p {
    margin-bottom: 12px;
  }
}
</style>