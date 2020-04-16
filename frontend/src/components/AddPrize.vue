<template>
  <div class="container">
      <form v-on:submit.prevent="addOrUpdatePrize">
          <div id="left-side-of-form">
            <div class="form-group">
                <label>Prize Name<b-form-input type="text" v-model.lazy="prize.prizeName" required/></label>
            </div>
            <div class="form-group">
                <label>Description<b-form-input type="text" v-model.lazy="prize.prizeDescription" required/></label>
            </div>
            <div class="form-group">
                <label>Minutes To Achieve It<b-form-input type="text" v-model.lazy="prize.minutesNeeded" required/></label>
            </div>
            <div class="form-group">
                <p>Who should the prize be available to?</p>
                    <select v-model.lazy="prize.userRole">
                        <option value="parent">Parent</option>
                        <option value="child">Child</option>
                        <option value="both">Both</option>
                    </select>
            </div>
          </div>
          <div id="right-side-of-form">
            <div class="form-group">
                <label v-if="this.prize.prizeId">Number of This Prize Left<b-form-input type="text" v-model.lazy="prize.maxPrizes" required/></label>
                <label v-else>How Many of This Prize Do You Want to Make?<b-form-input type="text" v-model.lazy="prize.maxPrizes" required/></label>
            </div>
            <div class="form-group">
                <label>Start Date<b-form-input type="date" v-model.lazy="prize.beginDate" required/></label>
            </div>
            <div class="form-group">
                <label>End Date<b-form-input type="date" v-model.lazy="prize.endDate" required/></label>
            </div>
            <button v-if="prize.prizeId" class="btn btn-info margin-button" type="submit">Update Prize</button>
            <button v-if="prize.prizeId" v-on:click.prevent="closeUpdateForm" class="btn btn-outline-dark edit-form-buttons">Close</button>
            <button v-else class="btn btn-success" type="submit">Add Prize</button>
          </div>
      </form>
  </div>
</template>

<script>
import auth from '@/auth';
export default {
  props: {
      currentUser: Object,
      prize: Object
  },
  methods: {
      addOrUpdatePrize() {
          if (this.prize.prizeId) {
            fetch(`${process.env.VUE_APP_REMOTE_API}/api/prize/${this.prize.prizeId}`, {
                method: 'PUT',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + auth.getToken()
                    },
                body: JSON.stringify(this.prize),
            })
            .then(() => this.$emit('add-prize'))
          } else {
               fetch(`${process.env.VUE_APP_REMOTE_API}/api/prize/`, {
                    method: 'POST',
                    headers: {
                        Accept: 'application/json',
                        'Content-Type': 'application/json',
                        Authorization: 'Bearer ' + auth.getToken()
                        },
                    body: JSON.stringify(this.prize),
                })
                .then(() => this.$emit('add-prize'))
          }
      },
      closeUpdateForm() {
          this.$emit('close-update-form');
      }
  }
}
</script>

<style scoped>
p {
    margin: 0;
}
form {
    display: flex;
    justify-content: space-between;
}
#left-side-of-form, #right-side-of-form{
    width: 45%;
    margin: 0;
}
.margin-button {
    margin-right: 5%;
}
</style>