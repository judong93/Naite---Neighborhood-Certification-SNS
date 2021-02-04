<template>
    <div id='boardlistdetail'>
        <div class='detailInfo'>
            <div class="detailHead">
                <div class="detailUser">
                    <img src="../../assets/cha2.png">
                </div>
                <div>
                    <div class="detailTitle">{{apiData.boardTitle}}</div>
                    <div class="detailBar">
                        <i class="far fa-comments"></i>
                        <span>메세지</span>
                        <i class="fas fa-ban"></i>
                        <span>신고</span>
                    </div>
                    <div class='detailHeadInfo'>
                        <div class='detailUserNick' v-if='apiData.unknownFlag'>익명님 {{categoryName[apiData.bigCategoryNo]}}에 남긴 글</div>
                        <div class='detailUserNick' v-else>{{apiData.userNick}}님이 {{categoryName[apiData.bigCategoryNo]}}에 남긴 글 </div>
                        <div>
                            <i class="far fa-thumbs-up"></i>
                            <span>{{apiData.boardLikeCnt}}</span>
                            <i class="far fa-comment-dots"></i>
                            <span>{{apiData.boardReportCnt}}</span>
                            <i class="far fa-clock"></i>
                            <span>{{apiData.boardCreatedAt}}</span>
                        </div>


                    </div>
                </div>
            </div>
            <hr style='background-color:white;margin:10px;'>
            <div class="detailBody">
                {{apiData.boardContent}}
            </div>
            <div class="detailFooter">

            </div>
        </div>
        <div class='detailImg' v-if='apiData.boardPic'>
        <!-- <div class='detailImg'> -->
            <img src="../../assets/firstpage3.jpg" alt="" width='100%'>
        </div>

    </div>
</template>

<script>
export default {
    name:'BoardListDetail',
    props:{
        apiData:Object,
    },
    data:function(){
        return {
            categoryName:['','번화가','동사무소','수군수군','소리소문','장터']
        }
    },
    watch:{
        apiData:function(){
            const img = this.apiData.boardPic
            const headBottom = document.querySelector('.detailHeadInfo')
            const detailBar = document.querySelector('.detailBar')
            if (!img) {
                headBottom.style.width = '1038px'
                detailBar.style.right ='1%'
            } else {
                headBottom.style.width = '740px'
                detailBar.style.right ='27%'
            }
        },
        
    }
}
</script>

<style>

#boardlistdetail{
    position:absolute;
    width: 60%;
    height: 40%;
    background-color: #A87A4F;
    top: 13%;
    left: 20%;  
    display:flex;  
    padding:10px;
    color:white;
}

.detailInfo {
    width: 100%;
    height: 100%;
    background-color: teal;
    font-family: font1;
    text-align: left;
}


.detailImg {
    width:400px;
    height: 100%;
    background-color: white;
}

.detailHead {
    width: 100%;
    height: 20%;
    display:flex;
}
.detailTitle {
    width: 80%;
    height:48px;
    text-align: left;
    font-size: 32px;
    overflow: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    margin-left: 20px;
}
.detailUserNick {
    text-align: left;
}


.detailUser > img {
    height: 73px;
    border: 1px solid rgb(172, 172, 172);
    border-radius: 20%;
    background-color: white;
}

.detailHeadInfo{
    width: 740px;    
    margin-left: 20px;
    display: flex;
    justify-content: space-between;
    color:white;
    font-size: 12px;
    margin-top: 4px;
}

.detailHeadInfo i {
    margin: 0 5px;
    margin-right: 10px;
}
.detailHeadInfo span{
    margin-right: 5px;
}

.detailBody {
    padding:0 10px;
}

.detailBar {
    position: absolute;
    top:2.3%;    
    right:27%;
    
}
.detailBar > span {
    margin-right: 10px;
    margin-left: 5px;
}

</style>