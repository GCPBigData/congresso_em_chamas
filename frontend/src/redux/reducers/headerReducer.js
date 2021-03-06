import * as HEADER_ACTIONS from "../actions/headerActions";

const initialState = {
  profile: {},
  loading: false
};

export default function headerReducer(state = initialState, action) {
  switch (action.type) {
    case HEADER_ACTIONS.REQUEST_PROFILE:
      return Object.assign({}, state, {
        loading: true
      });
    case HEADER_ACTIONS.RECEIVE_PROFILE:
      return Object.assign({}, state, {
        profile: action.response,
        loading: false
      });
    case HEADER_ACTIONS.FAILED_PROFILE:
      return Object.assign({}, state, {
        loading: false
      });
    default:
      return Object.assign({}, state);
  }
}
