<template>
  <div id="eval-showing-form">
    <div class="joiner-info">
      <div class="joiner-div">작성자 {{sellerUserNick}}님의 평가</div>
      <div class="joiner-div">평점: {{buyerScore}}</div>
      <div class="eval-comment">{{buyerComment}}</div>
    </div>
    <div class="joiner-info">
      <div class="joiner-div">참여자 {{buyerUserNick}}님의 평가</div>
      <div class="joiner-div">평점: {{sellerScore}}</div>
      <div class="eval-comment">{{sellerComment}}</div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: 'ProfileImgChangingForm',
  data: function () {
    return {
      sellerUserNick: '',
      buyerUserNick: '',
      buyerScore: 0,
      buyerComment: '',
      sellerScore: 0,
      sellerComment: '',
    }
  },
  props: {
    MarketNo: Number,
  },
  methods: {
    checkSpace:function () {
      event.returnValue=false;
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
    axios.get(`${SERVER_URL}/market/eval/${this.MarketNo}`)
      .then((res) => {
        this.sellerScore = res.data.data.sellerScore
        this.buyerScore = res.data.data.buyerScore
        this.sellerComment = res.data.data.sellerComment
        this.buyerComment = res.data.data.buyerComment
        this.buyerUserNick = res.data.data.buyerUserNick
        this.sellerUserNick = res.data.data.sellerUserNick
      })
  },
  watch: {
    MarketNo: function () {
      axios.get(`${SERVER_URL}/market/eval/${this.MarketNo}`)
        .then((res) => {
          this.sellerScore = res.data.data.sellerScore
          this.buyerScore = res.data.data.buyerScore
          this.sellerComment = res.data.data.sellerComment
          this.buyerComment = res.data.data.buyerComment
          this.buyerUserNick = res.data.data.buyerUserNick
          this.sellerUserNick = res.data.data.sellerUserNick
        })
    }
  },
}
</script>

<style>
  #eval-showing-form {
    position: relative;
    overflow: hidden;
    height: 80%;
    width: 100%;
    padding: 0 10%;
  }
  .joiner-info {
    position: relative;
    height: 43%;
  }
  .joiner-info:nth-child(2) {
    margin-top: 5%;
  }
  .joiner-div:nth-child(1) {
    font-size: 120%;
  }
  .joiner-div {
    position: relative;
    height: 11.75%;
    margin-bottom: 3%;
    /* border: 3px white solid; */
  }
  .eval-comment {
    position: absolute;
    height: 67%;
    bottom: 0;
    width: 100%;
    padding-bottom: 4%;
    overflow: auto;
    background-color: #ffec82;
    border-radius: 10px;
    /* border: 3px white solid; */
  }
</style>