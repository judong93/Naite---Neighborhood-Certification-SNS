<template>
    <div id="postingBoard">
        <div class="postingHead">
            <label for="postingTitle">제목: </label>
            <input type="text" id='postingTitle' v-model="params.boardTitle">
            <select name="카테고리"  v-model="params.bigCategoryNo">
                <option value="" selected disabled hidden >카테고리를 선택해주세요</option>
                <option value="1">번화가</option>
                <option value="2">동사무소</option>
                <option value="3">수군수군</option>
                <option value="4">소리소문</option>
                <option value="5">장터</option>
            </select>
            
        </div>
        <div class="postingBody">
            <label for="postingContent">내용</label><br>
            <textarea name="" id="postingContent" cols="90" rows="10" v-model="params.boardContent"></textarea>
            <label for="postingImg" >이미지</label>
            <input type="text" id='postingImg' v-model="params.boardPic">
        </div>
        <button @click='toggleFlag' class='postingFlag'>{{flag}}</button>
        <button @click='createForm'>test</button>
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
    }

}
</script>

<style>
#postingBoard {
    position: absolute;
    top: 15%;
    left: 20%;
    width: 60%;
    height: 83%;
    background-color: rgb(175, 219, 248);
    text-align: left;
    border-radius: 20px;
    padding: 10px 30px;
}

#postingBoard label {
    font-size: 32px;
    margin-right: 10px;
}

#postingBoard input {
    border:none;
    border-bottom: 1px solid black;
    background-color: transparent;
    outline: none;
}

#postingTitle {
    width: 60%;
    height: 32px;
    margin-right: 20px;
    font-size: 32px;
}

#postingContent {
    font-size: 20px;
    border-radius: 20px;
}

.postingFlag {
    background-color: rgb(167, 167, 245);
    color:white;

}



</style>