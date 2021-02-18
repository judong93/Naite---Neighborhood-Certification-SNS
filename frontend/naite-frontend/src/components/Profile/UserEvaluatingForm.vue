<template>
  <div id="user-evaluating-form">
    <textarea v-model="evalComment" name="" id="" cols="30" rows="10"></textarea>
    <StarRating @selectStar="selectStar" />
    <button class="setting-btn" @click="evaluateUser">평가 및 거래완료</button>
  </div>
</template>

<script>
import axios from 'axios'
import StarRating from '@/components/StarRating'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'UserEvaluatingForm',
  data: function () {
    return {
      evalComment: '',
      evalScore: 0,
    }
  },
  components: {
    StarRating
  },
  props: {
    selectedUserNick: String,
    MarketNo: Number,
    isSeller: Number,
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
    selectStar: function (ratedStar) {
      this.evalScore = ratedStar
    },
    evaluateUser: function () {
      if (this.isSeller===0) {
        const params = {
          evalComment: this.evalComment,
          evalScore: this.evalScore
        }
        console.log(params)
        axios.post(`${SERVER_URL}/market/eval/${this.MarketNo}`, params, this.setToken())
          .then(() => {
            alert('평가가 완료되었습니다!')
            this.$emit('evalCompleted')

          })
          .catch((err) => {
            console.log(err)
          })
      } else {
        const params = {
          buyerNick: this.selectedUserNick,
          evalComment: this.evalComment,
          evalScore: this.evalScore
        }
        console.log(params.buyerNick)
        console.log(params.evalComment)
        console.log(params.evalScore)
        console.log(typeof(this.MarketNo))
        axios.put(`${SERVER_URL}/market/complete/${this.MarketNo}`,params, this.setToken())
          .then(() => {
            alert('평가가 완료되었습니다!')
            this.$emit('evalCompleted')
          })
          .catch((err) => {
            console.log(err)
          })
      }

    },
    watch: {
      MarketNo: function () {
        this.evalComment = ''
        this.evalScore = 0
      }
    }
  }
}
</script>

<style>
  #user-evaluating-form textarea {
    resize: none;
    border-radius: 10px;
    margin-bottom: 20px;
    margin-top: 0;
  }
  .setting-btn {
    margin-top: 40px;
  }
</style>