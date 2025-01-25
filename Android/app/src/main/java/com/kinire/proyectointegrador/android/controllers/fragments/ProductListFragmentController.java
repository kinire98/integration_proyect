package com.kinire.proyectointegrador.android.controllers.fragments;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.kinire.proyectointegrador.android.R;
import com.kinire.proyectointegrador.android.sqlite.ProductDAO;
import com.kinire.proyectointegrador.android.utils.ImageCache;
import com.kinire.proyectointegrador.android.parcelable_models.ParcelableProduct;
import com.kinire.proyectointegrador.android.ui.activities.ProductActionActivity;
import com.kinire.proyectointegrador.android.ui.fragments.products.ProductsListFragment;
import com.kinire.proyectointegrador.android.ui.fragments.products.ProductsListViewModel;
import com.kinire.proyectointegrador.android.utils.UserAdmin;
import com.kinire.proyectointegrador.client.Connection;
import com.kinire.proyectointegrador.components.Product;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Clase controlador de la lista de productos.
 */
public class ProductListFragmentController implements AdapterView.OnItemClickListener {

    private ProductsListViewModel viewModel;

    private ProductsListFragment fragment;

    private final String PRODUCT_PARCELABLE_KEY;

    private final String AMOUNT_PARCELABLE_KEY;

    private final String POSITION_PARCELABLE_KEY;

    private final Logger logger = Logger.getLogger(ProductListFragmentController.class.getName());

    private final ProductDAO productDAO;

    private boolean productsInDB = false;

    private final UserAdmin userAdmin;

    private static boolean firstTimeLoading = true;

    public ProductListFragmentController(ProductsListFragment fragment, ProductsListViewModel viewModel) {
        this.fragment = fragment;
        this.viewModel = viewModel;
        this.PRODUCT_PARCELABLE_KEY = fragment.getString(R.string.product_parcelable_key);
        this.AMOUNT_PARCELABLE_KEY = fragment.getString(R.string.amount_parcelable_key);
        this.POSITION_PARCELABLE_KEY = fragment.getString(R.string.position_parcelable_key);
        this.productDAO = new ProductDAO(fragment.requireContext());
        this.userAdmin = new UserAdmin(fragment.requireContext());
        fragment.requireActivity().runOnUiThread(() -> {
            viewModel.setProducts(new ArrayList<>());
        });
        initProducts();
    }

    /**
     * Se pregunta al DAO de los productos si hay alguno guardado.
     * Si sí, se piden y se ponen en la vista.
     */
    private void initProducts() {
        if(productDAO.areThereProducts()) {
            if(firstTimeLoading) {
                List<Product> products = productDAO.getProducts();
                products.forEach(product -> productGot(product, true));
                this.productsInDB = true;
                firstTimeLoading = false;
            } else {
                List<Product> products = productDAO.getProductsWithoutImages();
                products.forEach(product -> productGot(product, true));
                this.productsInDB = true;
            }
        }
        initConnection();
    }

    /**
     * Aquí, lo primero que se mira es si la conexión está iniciada con lo que implica, poner las funciones anónimas
     * en case de que se pierda la conexión o se actualice algún producto.
     * Luego en cualquiera de los dos casos se establecen los productos en la vista
     */
    private void initConnection() {
        if(!Connection.isInstanceStarted()) {
            Connection.startInstance(() -> {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
                Connection.getInstance().setProductsUpdatedPromise(() -> {
                    Connection.getInstance().getNotStoredProducts(viewModel.getProductsData(), product -> productGot(product, false), (e) -> fragment.error());
                });
                Connection.getInstance().setConnectionLostPromise(fragment::errorConnectionLost);
                setProducts();
            });
        } else {
            Connection.getInstance().setProductsUpdatedPromise(() -> {
                Connection.getInstance().getNotStoredProducts(viewModel.getProductsData(), product -> productGot(product, false), (e) -> fragment.error());
            });
            Connection.getInstance().setConnectionLostPromise(fragment::errorConnectionLost);
            setProducts();
        }
    }

    /**
     * Si hay productos en la vista, entonces se pregunta al servidor por los productos que no se tienen guardados actualmente.
     * Si no, se piden todos.
     */
    private void setProducts() {
        if(!this.productsInDB)
            Connection.getInstance().getProducts(product -> productGot(product, false), (e) -> {fragment.error();});
        else
            Connection.getInstance().getNotStoredProducts(viewModel.getProductsData(), product -> productGot(product, false), (e) -> fragment.error());
    }

    /**
     * Este método recibe los productos, sea en local o en remoto. Guarda la imagen en una "caché" de imágenes
     * para poder acceder fácilmente a ellas desde cualquier sitio del programa. Y así no sea necesario cargarlas
     * más de una vez, ya que ya se encontrarán en memoria. Si el producto es recibido desde local, no será
     * insertarlo en el SQLite local.
     * Luego se procederá a introducirlo en el MutableLiveData que se encarga de la persistencia de los datos
     * de los productos y se establece. Una vez establecido se mostrará directamente en la lista
     * @param product El producto a mostrar
     * @param fromLocal Indicador para saber si el producto está guardado en local o ha sido recibido en remoto
     */
    private void productGot(Product product, boolean fromLocal) {
        if(product.getImage() != null)
            ImageCache.setImage(product.getImagePath(), Drawable.createFromStream(new ByteArrayInputStream(product.getImage()), "remote"));
        if(!fromLocal) {
            productDAO.insertProduct(product);
        }
        List<Product> products = viewModel.getProductsData();
        products.add(product);
        fragment.requireActivity().runOnUiThread(() -> {
            viewModel.setProducts(products);
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // If image not loaded, it won't open the product
        if(((ImageView) view.findViewById(R.id.image_field)).getDrawable() == null)
            return;
        if(userAdmin.getUser().isAdmin())
            return;
        Product product = viewModel.getProductsData().get(position);
        Intent intent = new Intent(fragment.requireActivity(), ProductActionActivity.class);
        if(ShoppingCartFragmentController.productExists(product)) {
            intent.putExtra(AMOUNT_PARCELABLE_KEY, ShoppingCartFragmentController.getAmount(product));
            intent.putExtra(POSITION_PARCELABLE_KEY, ShoppingCartFragmentController.getPosition(product));
        }
        intent.putExtra(PRODUCT_PARCELABLE_KEY, new ParcelableProduct(product));
        fragment.requireActivity().startActivity(intent);
    }
}
