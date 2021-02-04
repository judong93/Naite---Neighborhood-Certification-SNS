<template>
    <div id="postingBoard">
        <div class="postingHead">
            <input type="text" id='postingTitle' v-model="params.boardTitle" maxlength="30" placeholder="제목을 입력해주세요(최대 30자 가능)"><br>
            <select name="카테고리"  v-model="params.bigCategoryNo">
                <option value="" selected disabled hidden >카테고리를 선택해주세요</option>
                <option value="1">번화가</option>
                <option value="2">동사무소</option>
                <option value="3">수군수군</option>
                <option value="4">소리소문</option>
                <option value="5">장터</option>
            </select>
            <select name="소카테고리" v-model='smallCategoryNo' v-if='params.bigCategoryNo ==="3"'>
                <option value="" selected disabled hidden >세부카테고리</option>
                <option value="1">식당</option>
                <option value="2">의료</option>
                <option value="3">체육시설</option>
                <option value="4">미용</option>
                <option value="5">기타</option>
            </select>
            <select name="소카테고리" v-model='smallCategoryNo' v-if='params.bigCategoryNo ==="5"'>
                <option value="" selected disabled hidden >세부카테고리</option>
                <option value="1">공동구매</option>
                <option value="2">중고거래</option>
            </select>
            <select class='star' name="별점" v-model='reviewStar' v-if='params.bigCategoryNo==="3"'>
                <option value="" selected disabled hidden >별점을 선택해주세요!</option>
                <option value="1"> &#xf005;</option>
                <option value="2">&#xf005;&#xf005;</option>
                <option value="3">&#xf005;&#xf005;&#xf005;</option>
                <option value="4">&#xf005;&#xf005;&#xf005;&#xf005;</option>
                <option value="5">&#xf005;&#xf005;&#xf005;&#xf005;&#xf005;</option>
            </select>
        </div>
        <div class="postingBody">            
            <textarea id="postingContent" cols="87" rows="18" v-model="params.boardContent" :placeholder="contentExplain"></textarea>
            
        </div>
        <footer class='postingFooter'>
            <div class="postingImg">&#xf302;</div>
            <div>
                익명으로 게시하기
                <input type="checkbox">
                <button>작성완료</button>
            </div>

        </footer>

    </div>
</template>

<script>
export default {
    name:'postingBoard',
    data:function(){
        return{
            params:{
                "bigCategoryNo": "",
                "boardContent": "",
                "boardPic": "",
                "boardTitle": "",
                "openFlag": 0,
                "unknownFlag": 0,
                "userNo": 2,
            },
            smallCategoryNo:0,
            reviewStar:0,
            contentExplain:'',
            flag:'실명'
        }
    },
    methods:{
        createForm:function(){
            // 추후 널값확인필요
            this.$emit('createForm',this.params)

        },
        toggleFlag:function(){
            const btn = document.querySelector('.postingFlag')
            if (this.flag==='실명'){
                this.flag='익명'
                this.params.openFlag = 0
                this.params.unknownFlag = 1
                btn.style.backgroundColor='red'

            } else {
                this.flag='실명'
                this.params.openFlag = 1
                this.params.unknownFlag = 0
                btn.style.backgroundColor='rgb(167, 167, 245)'
            }
        }
    },
    watch:{
        params : function(){
            if (!this.params.bigCategoryNo){
                this.contentExplain = '[주의사항]'
            }
        }
    },
    created:function(){
        this.contentExplain = '카테고리를 선택해주세요!'+'\n*수군수군 및 장터는 세부 카테고리를 추가로 선택해주세요!*\n\n'+'1.번화가 : 번화가는 자유게시판 입니다. 자유롭고 다양한 글을 남겨주세요!\n\n2.동사무소 : 동사무소는 질문게시판입니다. 우리동네 혹은 이웃에게 궁금한 것을 적어보세요. \n\n 3. 수군수군 : 수군수군은 리뷰게시판입니다. 우리동네 시설을 이용하고 느끼신점을 작성해주세요!\n\n 4. 소리소문 : 소리소문은 핫이슈게시판입니다. 우리동네 할인정보/교통/동네 뉴스 등 여러 소식을 작성해주세요. \n\n 5.장터 : 장터는 공동구매 혹은 중고거래를 위한 게시판입니다. 이 게시판을 통해 알뜰한 소비하세요! \n\n **일부게시물은 익명사용이 제한됩니다.**'
        }
}
</script>

<style>
#postingBoard {
    position: absolute;
    top: 13%;
    left: 20%;
    width: 60%;
    height: 83%;
    background-color:#A87A4F;
    text-align: left;
    border-radius: 20px;
    padding: 10px 30px;
    border: 10px solid #3F9F47;
}

#postingBoard label {
    font-size: 32px;
    margin-right: 10px;
}

#postingBoard input {
    border:none;
    border-bottom: 1px solid rgb(255, 255, 255);
    background-color: transparent;
    outline: none;
    padding: 5px;
}

#postingTitle {
    width: 100%;
    height: 50px;
    margin-right: 20px;
    font-size: 32px;
    
    
}

.postingHead > select {
    margin-top: 20px;
    background-color: transparent;
    color:white;
    margin-right: 20px;
    border-color: white;    
}
.postingHead > select > option {
    color:white;
    background-color: #3F9F47;
}
.postingHead > select > option > i {
    color:rgb(255, 251, 16);
}



#postingContent {
    font-size: 20px;
    border-radius: 20px;
    background-color: transparent;
    border: 1px solid white;
    color:white;
    margin-top:20px;
    outline: none;
    padding:10px;
    resize:none;

}

.postingFlag {
    background-color: rgb(167, 167, 245);
    color:white;

}

.star {
    font-family: 'Font Awesome 5 Free';
    font-weight: 900;
}

.postingFooter {
    display: flex;
    width:100%;
    height: 10%;
    justify-content: space-between;
}

.postingImg {
    font-family: 'Font Awesome 5 Free';
    font-size: 30px;
    margin: 10px 20px 0;
}
.postingImg + div{
    margin-top: 20px;
}
.postingImg + div > input {
    margin-right: 20px;
}






</style>