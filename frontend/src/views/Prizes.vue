<template>
    <div class="container outer-container">
        <h3>Prizes</h3>
        <div id="top-image">
            <img id="prizeImage" src='@/assets/images/prizeheader.jpg'/>
        </div>
        <b-alert
          :show="dismissCountDown"
          dismissible
          variant="success"
          @dismissed="dismissCountDown=0"
          @dismiss-count-down="countDownChanged"
        >
          <p>Congratulations on your prize!</p>
        </b-alert>
        <h5>Your Total Available Minutes: {{availableMinutes}} </h5>
        <button class="btn btn-info" id="addPrizeButton" v-on:click="togglePrizeForm"
        v-if="currentUser.rol === 'parent'"> {{formButtonText}} </button>
        <div id="prizeForm" v-if="showPrizeForm">
          <add-prize v-on:add-prize="toggleFormAndLoadPrizes" v-bind:currentUser="currentUser"
          v-bind:prize="prizeToUpdate" v-on:close-update-form="closeUpdateForm"/>
        </div>
        <div id="noPrizeCard" v-if="prizes.length === 0">
              <img id="morePrizesSoon" src="@/assets/images/moreprizesfancyfont.jpg"/>
        </div>
        <div class="prizeCards">
            <div class="prize" v-for="prize in prizes" v-bind:key="prize.prizeId">
              <div class="trophy">
                <img class="trophyImg" src="@/assets/images/trophyicon.jpg"/>
              </div>
              <div class="prizeContent">
                <div class="title">
                  <span class ="bold"> {{prize.prizeName}} </span>
                </div>
                <p>Start Date: {{prize.beginDate}}</p>
                <p>End Date: {{prize.endDate}}</p>
                <p>Description: {{prize.prizeDescription}}</p>
                <p>User group available to: {{prize.userRole}}</p>
                <p>Number of This Prize Left: {{prize.maxPrizes}}</p>
                <span class="bold">Minutes Needed: {{prize.minutesNeeded}}</span>
              </div>
              <div class="buttons">
                <div class="select">
                  <button v-if="availableMinutes < prize.minutesNeeded || (prize.userRole === 'child' && currentUser.rol === 'parent')" class="btn btn-secondary">Claim Prize</button>
                  <button v-else class="btn btn-success" v-on:click.prevent="claimPrize(prize)">Claim Prize</button>
                  
                </div>
                <div class="editPrize" v-if="currentUser.rol === 'parent'">
                  <button v-on:click.prevent="updatePrize(prize)" class="btn btn-warning right-inline-button">Edit Prize</button>
                  <button class="btn btn-danger" v-on:click.prevent="removePrize(prize)">Remove Prize</button>
                </div>
              </div>
            </div>
        </div>
    </div>
  
</template>

<script>
import auth from '@/auth';
import AddPrize from '@/components/AddPrize.vue';
export default {
  props: {
    currentUser: Object
  },
  components: {
    AddPrize
  },
  data() {
      return{
        showPrizeForm: false,
        formButtonText: 'Add a New Prize!',
        familyPrizes: [],
        currentFamilyPrizes: [],
        prizeToUpdate: {
            prizeId: '',
            familyId: this.currentUser.familyId,
            prizeName: '',
            prizeDescription: '',
            minutesNeeded: '',
            userRole: '',
            maxPrizes: '',
            beginDate: '',
            endDate: ''
        },
        blankPrize: {},
        logs: [],
        prizesClaimed: [],
        availableMinutes: '',
        dismissSecs: 4,
        dismissCountDown: 0,
        currentYear: '',
        currentMonth: '',
        currentDay: '',
        date: ''
      }
  },
  methods: {
    togglePrizeForm() {
      this.prizeToUpdate = this.makeBlankPrize();
      this.showPrizeForm = !this.showPrizeForm;
      this.showPrizeForm ? this.formButtonText = 'Hide Form' : this.formButtonText = 'Add a New Prize!';
    },
    makeBlankPrize() {
      return {
          prizeId: '',
          familyId: this.currentUser.familyId,
          prizeName: '',
          prizeDescription: '',
          minutesNeeded: '',
          userRole: '',
          maxPrizes: '',
          beginDate: '',
          endDate: ''
      }
    },
    toggleFormAndLoadPrizes() {
      this.prizeToUpdate = this.blankPrize;
      this.prizes = [];
      this.familyPrizes =[];
      this.currentFamilyPrizes = [];
      this.loadAllFamilyPrizes();
      this.togglePrizeForm();
    },
    loadAllFamilyPrizes() {
       fetch(`${process.env.VUE_APP_REMOTE_API}/api/prizes/${this.currentUser.familyId}`, {
        method: 'GET',
          headers: {
          Accept: 'application/json',
          Authorization: 'Bearer ' + auth.getToken()
        }
        })
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          this.familyPrizes = data;
          this.setCurrentDate();
          this.filterOldAndFuturePrizes();
        });
    },
    updatePrize(prize) {
      this.prizeToUpdate = prize;
      this.showPrizeForm = true;
    },
    closeUpdateForm() {
      this.showPrizeForm = false;
      this.prizeToUpdate = this.blankPrize;
      this.loadAllFamilyPrizes();
    },
    removePrize(prize) {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/prize/remove/${prize.prizeId}`, {
        method: 'PUT',
          headers: {
          Accept: 'application/json',
          Authorization: 'Bearer ' + auth.getToken()
        }
        })
        .then(() => this.loadAllFamilyPrizes());
    },
    claimPrize(prize) {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/prize/claim/${prize.prizeId}`, {
        method: 'PUT',
          headers: {
          Accept: 'application/json',
          Authorization: 'Bearer ' + auth.getToken()
        }
        })
        .then(
          fetch(`${process.env.VUE_APP_REMOTE_API}/api/awardedprize/${prize.prizeId}/${this.currentUser.id}`, {
            method: 'POST',
            headers: {
            Accept: 'application/json',
            Authorization: 'Bearer ' + auth.getToken()
          }
          })
          )
        .then(() => this.showAlert(),
                    this.loadAllFamilyPrizes(),
                    this.getTotalUserMinutes());
    },
    getTotalUserMinutes() {
      this.getAllLogs();
      this.getAllPrizes();
    },
    getAllLogs() {
        fetch(`${process.env.VUE_APP_REMOTE_API}/api/activity/user/${this.currentUser.id}`, {
          method: 'GET',
            headers: {
            Accept: 'application/json',
            Authorization: 'Bearer ' + auth.getToken()
          }
          })
          .then((response) => {
            return response.json();
          })
          .then((data) => {
            this.logs = data;
          })
          .then(this.getAllPrizes());
    },
    getAllPrizes() {
      fetch(`${process.env.VUE_APP_REMOTE_API}/api/userawardedprizes/${this.currentUser.id}`, {
          method: 'GET',
            headers: {
            Accept: 'application/json',
            Authorization: 'Bearer ' + auth.getToken()
          }
          })
          .then((response) => {
            return response.json();
          })
          .then((data) => {
            this.prizesClaimed = data;
          })
        .then(() => this.computeAvailableMinutes());
    },
    computeAvailableMinutes() {
      this.availableMinutes = this.totalMinutes - this.spentMintues;
    },
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown;
    },
    showAlert() {
      this.dismissCountDown = this.dismissSecs
    },
    setCurrentDate() {
      this.date = new Date();
      this.currentYear = this.date.getFullYear();
      this.currentMonth = this.date.getMonth() + 1;
      this.currentDay = this.date.getDate();

    },
    filterOldAndFuturePrizes() {
      this.currentFamilyPrizes = [];
      this.familyPrizes.forEach(prize => {
         if (this.currentYear <= prize.endDate.substring(0, 4)) {
          if (this.currentMonth <= prize.endDate.substring(5, 7)) {
            if (this.currentDay <= prize.endDate.substring(8)) {
              if (this.currentYear >= prize.beginDate.substring(0,4)) {
                if (this.currentMonth >= prize.beginDate.substring(5, 7)) {
                  if (this.currentDay >= prize.beginDate.substring(8)) {
                    this.currentFamilyPrizes.push(prize);
                  }
                }
              }
            }
          }
        }
      });
    }
  },
  created() {
    this.loadAllFamilyPrizes();
    this.getTotalUserMinutes(); 
    this.blankPrize = this.makeBlankPrize();
    
  },
  computed: {
    prizes: function() {
      if (this.currentUser.rol === 'parent') {
        return this.currentFamilyPrizes;
      } else {
        return this.currentFamilyPrizes.filter(prize => prize.userRole != 'parent');
      }
    },
    totalMinutes: function() {
      return this.logs.reduce((total, current) => 
        total + current.minutesSpent, 0
      )
    },
    spentMintues: function() {
      return this.prizesClaimed.reduce((total, current) =>
        total + current.minutesNeeded, 0
      )
    }
  }

}
</script>

<style scoped>

  div#top-image {
    max-height: 200px; 
    /* make responsive */
    overflow: hidden;
    border-radius: 15px;
    margin-bottom: 2%;
  }

  #prizeImage {
    width: 91vw;
    height: auto;
       /* make responsive */
  }

  .bold{
    font-weight: bold;
  }

  .prizeCards{
    display: flex;
    flex: row;
    flex-wrap: wrap;
    width: 91 vw;
  }

  div.prize{
    border: 2px solid lightgrey;
    border-radius: 5px;
    margin: 1%;
    padding: 10px;
    width: 30%;
    max-height: 50%;
  }

  .trophyImg{
    width: 17%;
  }

  .select, .editPrize{
    margin: 5%;
  }

  div#prizeForm {
    margin-top: 2%;
    border: 1px solid grey;
    width: 80%;
    border-radius: 15px;
    padding: 10px;
  }
  div.outer-container {
    margin-bottom: 3%;
  }
  .right-inline-button {
    margin-right: 3%;
  }
  .title {
    margin-top: 3%;
    margin-bottom: 3%;
    font-size: 1.3rem;
  }
  div.trophy {
    display: flex;
    justify-content: flex-end;
  }
  .prizeContent {
    margin-top: -23%;
    margin-left: 6%;
  }

  #addPrizeButton {
    margin-bottom: 1%;
    margin-top: 1%;
  }
  #noPrizeCard {
    width: 120%;
  }
  div.prize h4 {
    margin-top: 5%;
    margin-bottom: -13%;
  }
  p {
    margin: 1%;
  }
  span.bold {
    margin-left: 1%;
  }

  #morePrizesSoon{
    max-height: 30vw;
    border-radius: 10px;
  }

  #noPrizeCard{
    margin-top: 3%
  }
</style>