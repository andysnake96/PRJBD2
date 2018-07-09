package GUI;

public interface

Initializable {
    /*
    interface for all gui controller that need reference passing
     */
    public void initialize();
    /* must do
        List<E> list;   //LIST SETTED AS ATTRI
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer param) {
                return createPage(list param) ;
            }
        });
     */

}
